package eu.venthe.platform.workflow.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.workflow.definition.contexts.jobs.JobWithStepsDefinition;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.application.utilities.CollectionUtilities;
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
public class WorkflowDefinitionJobsContext {
    @Singular
    private final Map<JobName, JobWithStepsDefinition> jobs;

    private WorkflowDefinitionJobsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        jobs = CollectionUtilities.iteratorToStream(root.fields())
                .collect(Collectors.toMap(
                        entry -> new JobName(entry.getKey()),
                        entry -> JobWithStepsDefinition.ensure(entry.getValue())
                ));
    }

    public static WorkflowDefinitionJobsContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, WorkflowDefinitionJobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    }
}
