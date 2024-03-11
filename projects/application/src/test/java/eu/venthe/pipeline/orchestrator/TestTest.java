package eu.venthe.pipeline.orchestrator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.gerrit.api.ChangesApi;
import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import eu.venthe.pipeline.gerrit.model.ProjectInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.This;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import static net.bytebuddy.implementation.MethodDelegation.to;

@Slf4j
@SpringBootTest
class TestTest {

    @Autowired
    ProjectsApi projectsApi;

    @Autowired
    ChangesApi changesApi;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void name() throws IOException {
        String result = projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
                .replaceAll("^\\)]}'", "");

        TypeReference<Map<String, ProjectInfo>> typeRef = new TypeReference<>() {
        };

        log.info("{}", objectMapper.readValue(result, typeRef));
    }

    @Configuration
    public static class Config {

        @Slf4j
        static class GenericInterceptor {
            public static <T> ResponseEntity<T> intercept(
                    @Origin Method method,
                    @AllArguments Object[] args,
                    @This Object zis
            ) throws RestClientException {
                log.info("!!!!");
                try {
                    // Invoke the original method
                    return (ResponseEntity<T>) method.invoke(zis, args);
                } catch (Exception e) {
                    // Handle exceptions
                    throw new RestClientException("Error invoking the method", e);
                } finally {
                    log.info("!!!!2");
                }
            }
        }

        @SneakyThrows
        @Bean
        ApiClient apiClient() {
            ApiClient apiClient = new ByteBuddy()
                    .subclass(ApiClient.class)
                    .method(ElementMatchers.named("invokeAPI")).intercept(to(GenericInterceptor.class))
                    .make()
                    .load(ApiClient.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
                    .getLoaded()
                    .newInstance();


//            ApiClient apiClient = new ApiClient();
            apiClient.setPassword("secret");
            apiClient.setUsername("admin");
            apiClient.setBasePath("http://localhost:15480");
            return apiClient;
        }

        @Bean
        ChangesApi ChangesApi(ApiClient apiClient) {
            return new ChangesApi(apiClient);
        }

        @Bean
        ProjectsApi ProjectsApi(ApiClient apiClient) {
            return new ProjectsApi(apiClient);
        }

        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }
}
