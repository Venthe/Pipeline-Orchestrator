package eu.venthe.platform.data_source_configuration.domain.plugins.gerrit;

import eu.venthe.platform.gerrit.invoker.ApiClient;

public class GerritApiClient extends ApiClient {
    public GerritApiClient(GerritConfiguration properties) {
        setBasePath(properties.getBasePath());
        setUsername(properties.getUsername());
        setPassword(properties.getPassword());
    }
}
