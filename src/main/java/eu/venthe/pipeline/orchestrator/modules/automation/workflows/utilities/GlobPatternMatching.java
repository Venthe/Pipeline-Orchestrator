package eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;

@UtilityClass
public class GlobPatternMatching {
    public static boolean isMatching(Glob pattern, Path path) {
        if (!path.toFile().isDirectory()) {
            throw new IllegalArgumentException();
        }

        return FilenameUtils.wildcardMatch(path.toString(), pattern.value());
    }

    public record Glob(String value) {
    }
}
