package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.gerrit.invoker.ApiClient;

public class GerritApiClient extends ApiClient {
    public GerritApiClient(GerritConfiguration properties) {
        setBasePath(properties.getBasePath());
        setUsername(properties.getUsername());
        setPassword(properties.getPassword());
    }
}
