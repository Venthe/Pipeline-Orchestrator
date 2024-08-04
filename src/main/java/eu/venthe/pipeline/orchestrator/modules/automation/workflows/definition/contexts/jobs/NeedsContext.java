package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ToString
@RequiredArgsConstructor
@Getter
public class NeedsContext {
    private final Set<JobId> needs;

    public static Optional<NeedsContext> create(final JsonNode value) {
        return ContextUtilities.create(value, node -> {
            if (node.isTextual()) {
                return new NeedsContext(Collections.singleton(new JobId(node.textValue())));
            } else if (node.isArray()) {
                return new NeedsContext(StreamSupport.stream(node.spliterator(), false)
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
