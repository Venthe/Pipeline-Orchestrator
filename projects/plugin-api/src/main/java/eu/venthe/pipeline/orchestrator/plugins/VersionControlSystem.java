package eu.venthe.pipeline.orchestrator.plugins;

import java.util.Optional;

public interface VersionControlSystem {
    Optional<byte[]> getFile(String repositoryId, String projectName, String ref, String workflow);
}
