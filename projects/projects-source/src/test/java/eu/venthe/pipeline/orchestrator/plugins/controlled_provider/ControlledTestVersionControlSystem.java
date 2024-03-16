package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;

import java.util.Optional;

public class ControlledTestVersionControlSystem implements VersionControlSystemProvider {
    @Override
    public Optional<byte[]> getFile(String projectName, String ref, String path) {
        throw new UnsupportedOperationException();
    }
}
