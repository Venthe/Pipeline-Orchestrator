package eu.venthe.pipeline.orchestrator.projects.domain.projects.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FilenameUtils;

@UtilityClass
public class GlobPatternMatching {
    public static boolean isMatching(String pattern, String path) {
        return FilenameUtils.wildcardMatch(path, pattern);
    }

}
