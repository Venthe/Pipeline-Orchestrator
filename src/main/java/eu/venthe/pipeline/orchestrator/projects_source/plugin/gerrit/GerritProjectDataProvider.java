package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectDataProvider;
import lombok.Value;

import java.nio.file.Path;
import java.util.Optional;

@Value
public class GerritProjectDataProvider implements ProjectDataProvider {
    public GerritProjectDataProvider(ApiClient apiClient) {
    }

    @Override
    public Optional<byte[]> getFile(String projectIdentifier, String revision, Path path) {
        throw new UnsupportedOperationException();
    }
}
