package eu.venthe.pipeline.orchestrator.modules.workflow.domain.utilities;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class PipelineUtilities {
    public static Path resolveFromOrchestratorDirectory(String filename) {
        return Path.of(".mantle", "workflows", filename);
    }

    public static Path resolveFromOrchestratorDirectory(Path filename) {
        return Path.of(".mantle", "workflows").relativize(filename);
    }
}
