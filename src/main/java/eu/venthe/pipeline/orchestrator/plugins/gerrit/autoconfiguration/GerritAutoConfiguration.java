package eu.venthe.pipeline.orchestrator.plugins.gerrit.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.gerrit.api.ChangesApi;
import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "gerrit", name = "autoconfiguration", matchIfMissing = false, havingValue = "true")
public class GerritAutoConfiguration {
    @SneakyThrows
    @Bean
    @ConditionalOnMissingBean({ApiClient.class})
    ApiClient apiClient(ObjectMapper objectMapper, GerritConfiguration configuration) {
        ApiClient apiClient = new ApiClient(objectMapper);
        apiClient.setPassword(configuration.getPassword());
        apiClient.setUsername(configuration.getUsername());
        apiClient.setBasePath(configuration.getBasePath());

        return apiClient;
    }

    @Bean
    ChangesApi changesApi(ApiClient apiClient) {
        return new ChangesApi(apiClient);
    }

    @Bean
    ProjectsApi projectsApi(ApiClient apiClient) {
        return new ProjectsApi(apiClient);
    }

    @Bean
    @ConditionalOnMissingBean({ObjectMapper.class})
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
