package eu.venthe.platform.application.plugins.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.platform.gerrit.api.ChangesApi;
import eu.venthe.platform.gerrit.api.RepositoryApi;
import eu.venthe.platform.gerrit.invoker.ApiClient;
import eu.venthe.platform.gerrit.model.RepositoryInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class BasicSystemApiTest {
    ChangesApi changesApi;
    RepositoryApi repositorysApi;

    @BeforeEach
    @SneakyThrows
    void setup() {
        ObjectMapper objectMapper = new ObjectMapper();

        ApiClient apiClient = new ApiClient(objectMapper);
        apiClient.setPassword("secret");
        apiClient.setUsername("admin");
        apiClient.setBasePath("http://localhost:15480");
        changesApi = new ChangesApi(apiClient);
        repositorysApi = new RepositoryApi(apiClient);
    }

    @Test
    void name() {
        Map<String, RepositoryInfo> result = repositorysApi.listRepository(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        log.info("{}", result);
    }
}
