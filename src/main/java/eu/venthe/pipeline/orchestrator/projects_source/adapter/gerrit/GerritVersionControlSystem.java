package eu.venthe.pipeline.orchestrator.projects_source.adapter.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.RepositoryReader;

import java.util.Optional;
import java.util.function.Function;

public class GerritVersionControlSystem implements RepositoryReader {
    @Override
    public Optional<byte[]> getFile(String projectName, String ref, String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> Optional<T> getFile(String projectName, String ref, String path, Function<byte[], T> mapper) {
        throw new UnsupportedOperationException();
    }
}
