package eu.venthe.pipeline.workflows.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class WorkflowDefinitionStepsContext {
    private final List<? extends AbstractStepDefinition> steps;

    public static Optional<WorkflowDefinitionStepsContext> create(final JsonNode root) {
        if (root.isEmpty() || root.isNull()) {
            return Optional.empty();
        }

        List<? extends AbstractStepDefinition> collection = ContextUtilities.Collection.createCollection(
                root,
                e -> e.filter(Predicate.not(JsonNode::isEmpty))
                        .filter(Predicate.not(JsonNode::isNull))
                        .map(StepDefinitionFactory::create)
                        .toList());
        return Optional.of(new WorkflowDefinitionStepsContext(collection));
    }
}
