package eu.venthe.pipeline.orchestrator.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
public class BasicApiTest {
    ChangesApi changesApi;
    ProjectsApi projectsApi;

    @BeforeEach
    @SneakyThrows
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();

        ApiClient apiClient = new ApiClient(objectMapper);
        apiClient.setPassword("secret");
        apiClient.setUsername("admin");
        apiClient.setBasePath("http://localhost:15480");
        changesApi = ChangesApi(apiClient);
        ProjectsApi = ProjectsApi(apiClient);
    }

    @Test
    void name() {
        Map<String, ProjectInfo> result = projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        log.info("{}", result);
    }
}
