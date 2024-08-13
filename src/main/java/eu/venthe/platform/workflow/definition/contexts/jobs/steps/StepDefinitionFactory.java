package eu.venthe.platform.workflow.definition.contexts.jobs.steps;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;

public class StepDefinitionFactory {
    public static AbstractStepDefinition create(final JsonNode _root) {
        var root = ContextUtilities.validateIsObjectNode(_root);

        if (root.get("uses").textValue() != null && root.get("run").textValue() != null) {
            throw new UnsupportedOperationException();
        }

        if (root.get("uses").textValue() != null) {
            return new UsesStepDefinition(root);
        }

        if (root.get("run").textValue() != null) {
            return new RunStepDefinition(root);
        }

        throw new UnsupportedOperationException();
    }
}
