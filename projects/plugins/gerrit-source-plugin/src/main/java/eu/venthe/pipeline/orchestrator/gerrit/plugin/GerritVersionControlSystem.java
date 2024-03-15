package eu.venthe.pipeline.orchestrator.gerrit.plugin;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;

import java.util.Optional;

public class GerritVersionControlSystem implements VersionControlSystem {
    @Override
    public Optional<byte[]> getFile(String repositoryId, String projectName, String ref, String workflow) {
        throw new UnsupportedOperationException();
    }
}
