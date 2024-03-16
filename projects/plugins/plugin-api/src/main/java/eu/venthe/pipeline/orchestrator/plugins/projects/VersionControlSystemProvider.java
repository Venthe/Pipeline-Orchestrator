package eu.venthe.pipeline.orchestrator.plugins.projects;

import java.util.Optional;

public interface VersionControlSystemProvider {
    Optional<byte[]> getFile(String projectName, String ref, String path);
}
