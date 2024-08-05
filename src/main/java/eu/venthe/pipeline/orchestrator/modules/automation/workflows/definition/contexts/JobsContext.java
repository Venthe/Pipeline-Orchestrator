package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.JobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.stream.Collectors;

@SuperBuilder
@ToString
@Getter
// TODO: Implement context
public class JobsContext {
    @Singular
    private final Map<JobId, JobContext> jobs;

    private JobsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        jobs = CollectionUtilities.iteratorToStream(root.fields())
                .collect(Collectors.toMap(
                        e -> new JobId(e.getKey()),
                        e -> JobContext.ensure(e.getValue())
                ));
    }

    public static JobsContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, JobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    }
}
