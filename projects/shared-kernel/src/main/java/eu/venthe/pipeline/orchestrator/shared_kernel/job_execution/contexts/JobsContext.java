package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;

import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.sameKey;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.toMap;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class JobsContext {

    /**
     * The result of a job in the reusable workflow. Possible values are success, failure, cancelled, or skipped.
     */
    private final String result;

    /**
     * The set of outputs of a job in a reusable workflow.
     */
    @Singular
    private final Map<String, String> outputs;

    private JobsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        result = ContextUtilities.Text.ensure(root.get("result"));
        outputs = root.get("outputs").properties().stream()
                .map(sameKey(ContextUtilities.Text::ensure))
                .collect(toMap());
    }

    public static Map<String, JobsContext> ensure(JsonNode jobs) {
        return ContextUtilities.validateIsObjectNode(jobs).properties().stream()
                .map(sameKey(e -> ContextUtilities.ensure(e, JobsContext::new)))
                .collect(toMap());
    }
}
