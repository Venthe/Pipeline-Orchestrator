package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectDataProvider;

import java.nio.file.Path;
import java.util.Optional;

public class GerritVersionControlSystem implements ProjectDataProvider {
    @Override
    public Optional<byte[]> getFile(String projectIdentifier, String revision, Path path) {
        throw new UnsupportedOperationException();
    }
}
