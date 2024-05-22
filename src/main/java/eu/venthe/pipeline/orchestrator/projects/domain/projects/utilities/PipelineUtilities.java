package eu.venthe.pipeline.orchestrator.projects.domain.projects.utilities;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class PipelineUtilities {
    public static Path resolveFromOrchestratorDirectory(String filename) {
        return Path.of(".mantle", "workflows", filename);
    }
}
