package eu.venthe.platform.workflow.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.Optional;

public class StepDefinitionFactory {
    public static AbstractStepDefinition create(final JsonNode _root) {
        var root = ContextUtilities.validateIsObjectNode(_root);

        if (get(root, "uses").isPresent() && get(root, "run").isPresent()) {
            throw new UnsupportedOperationException();
        }

        if (get(root, "uses").isPresent()) {
            return new UsesStepDefinition(root);
        }

        if (get(root, "run").isPresent()) {
            return new RunStepDefinition(root);
        }

        throw new UnsupportedOperationException();
    }

    private static Optional<String> get(final ObjectNode root, String path) {
        return ContextUtilities.create(root.get(path), JsonNode::textValue);
    }
}
