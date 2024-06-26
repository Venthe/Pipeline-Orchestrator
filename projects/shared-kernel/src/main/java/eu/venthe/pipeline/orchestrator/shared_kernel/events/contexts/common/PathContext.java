package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PathContext {
    public static Path ensure(final JsonNode workflow) {
        return ContextUtilities.ensure(workflow, ContextUtilities.fromTextMapper(Path::of));
    }

    public static List<Path> list(JsonNode file) {
        return ContextUtilities.Collection.createCollection(
                file,
                stream -> stream
                        .map(PathContext::ensure)
                        .collect(Collectors.toList())
        );
    }
}
