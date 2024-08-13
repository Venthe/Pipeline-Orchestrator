package eu.venthe.platform.workflow.utilities;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class PipelineUtilities {
    public static Path resolveFromOrchestratorDirectory(String filename) {
        return Path.of(".mantle", "workflows", filename);
    }

    public static Path resolveFromOrchestratorDirectory(Path filename) {
        return Path.of(".mantle", "workflows").resolve(filename);
    }
}
