package eu.venthe.platform.workflow.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.workflow.definition.contexts.JobId;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.*;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ToString
@RequiredArgsConstructor
@Getter
@Builder
public class WorkflowDefinitionNeedsContext {
    @Singular
    private final Set<JobId> needs;

    public static Optional<WorkflowDefinitionNeedsContext> create(final JsonNode value) {
        return ContextUtilities.create(value, node -> {
            if (node.isTextual()) {
                return new WorkflowDefinitionNeedsContext(Collections.singleton(new JobId(node.textValue())));
            } else if (node.isArray()) {
                return new WorkflowDefinitionNeedsContext(StreamSupport.stream(node.spliterator(), false)
                        .filter(Predicate.not(JsonNode::isNull))
                        .filter(Predicate.not(JsonNode::isEmpty))
                        .map(JsonNode::textValue)
                        .map(JobId::new)
                        .collect(Collectors.toSet()));
            }

            throw new UnsupportedOperationException();
        });
    }
}
