package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class NeedsContext {
    private final Set<JobId> needs;

    public static Optional<NeedsContext> create(final JsonNode value) {
        return ContextUtilities.create(value, node -> {
            if (node.isTextual()) {
                return new NeedsContext(Collections.singleton(new JobId(node.toString())));
            } else if (node.isArray()) {
                return new NeedsContext(StreamSupport.stream(node.spliterator(), false).map(JsonNode::asText).map(JobId::new).collect(Collectors.toSet()));
            }

            throw new UnsupportedOperationException();
        });
    }
}
