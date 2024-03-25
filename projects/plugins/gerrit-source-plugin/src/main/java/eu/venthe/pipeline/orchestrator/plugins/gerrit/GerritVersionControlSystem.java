package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;

import java.util.Optional;
import java.util.function.Function;

public class GerritVersionControlSystem implements VersionControlSystemProvider {
    @Override
    public Optional<byte[]> getFile(String projectName, String ref, String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> getFile(String projectName, String ref, String path, Function<byte[], T> mapper) {
        throw new UnsupportedOperationException();
    }
}
