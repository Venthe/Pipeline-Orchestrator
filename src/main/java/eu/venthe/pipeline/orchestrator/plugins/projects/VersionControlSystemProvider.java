package eu.venthe.pipeline.orchestrator.plugins.projects;

import java.util.Optional;
import java.util.function.Function;

public interface VersionControlSystemProvider {
    Optional<byte[]> getFile(String projectName, String ref, String path);

    <T> Optional<T> getFile(String projectName, String ref, String path, Function<byte[], T> mapper);
}
