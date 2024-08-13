package eu.venthe.platform.workflow.runs._archive.run_context.contexts;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static eu.venthe.platform.application.utilities.CollectionUtilities.sameKey;
import static eu.venthe.platform.application.utilities.CollectionUtilities.toMap;

/**
 * For workflows with a matrix, the matrix context contains the matrix properties defined in the workflow file that
 * apply to the current job. For example, if you configure a matrix with the os and node keys, the matrix context object
 * includes the os and node properties with the values that are being used for the current job.
 * <p>
 * There are no standard properties in the matrix context, only those which are defined in the workflow file.
 * <p>
 * This context is only available for jobs in a matrix, and changes for each job in a workflow run. You can access this
 * context from any job or step in a workflow. This object contains the properties listed below.
 */
@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class MatrixContext {

    @JsonAnyGetter
    private final Map<String, JsonNode> matrix = new HashMap<>();

    @JsonCreator
    private MatrixContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        matrix.putAll(ContextUtilities.validateIsObjectNode(root).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, ContextUtilities.BooleanTextualNumber::ensure)))
                .collect(toMap()));
    }

    public static Optional<MatrixContext> create(JsonNode inputs) {
        return ContextUtilities.create(inputs, MatrixContext::new);
    }
}
