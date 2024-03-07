package eu.venthe.pipeline.orchestrator._archive2.plugins.api;

import java.util.Optional;

public interface VersionControlSystem {
    Optional<byte[]> getFile(String repositoryId, String projectName, String ref, String workflow);
}
