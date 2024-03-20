package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class PipelineUtilities {
    public static String resolveFromOrchestratorDirectory(String filename) {
        return Path.of(".mantle", "workflows", filename).toString();
    }
}
