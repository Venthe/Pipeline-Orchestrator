package eu.venthe.pipeline.orchestrator.projects_source.adapter.template;

import java.util.Optional;
import java.util.function.Function;

public interface ProjectDataAccessService {
    Optional<byte[]> getFile(String projectName, String ref, String path);

    <T> Optional<T> getFile(String projectName, String ref, String path, Function<byte[], T> mapper);
}
