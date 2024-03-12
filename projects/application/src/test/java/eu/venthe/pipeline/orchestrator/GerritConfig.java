package eu.venthe.pipeline.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.gerrit.api.ChangesApi;
import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GerritConfig {

    @SneakyThrows
    @Bean
    ApiClient apiClient(ObjectMapper objectMapper) {
        ApiClient apiClient = new ApiClient(objectMapper);
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
