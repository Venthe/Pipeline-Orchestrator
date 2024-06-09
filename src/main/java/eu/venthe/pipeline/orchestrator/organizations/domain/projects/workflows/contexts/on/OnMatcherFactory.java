package eu.venthe.pipeline.orchestrator.organizations.domain.projects.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.organizations.domain.domain.projects.domain.workflows.contexts.on.matchers.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.workflows.contexts.on.matchers.*;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

@UtilityClass
public class OnMatcherFactory {
    public static <T extends SystemEvent> Boolean map(String key, JsonNode _root, EventWrapper<T> event) {
        ObjectNode configuration = ContextUtilities.validateIsObjectNode(ContextUtilities.ensure(_root));

        if (EventTypeMatcher.matchType(key, "pull_request")) {
            return validOnEmpty(configuration, () -> votes(
                    OnActivityType.create(configuration).map(event::matches).orElse(true),
                    event.matches(OnBranches.create(configuration)),
                    event.matches(OnPaths.create(configuration))
            ));
        }

        if (EventTypeMatcher.matchType(key, "pull_request_target")) {
            return validOnEmpty(configuration, () -> votes(
                    OnActivityType.create(configuration).map(event::matches).orElse(true),
                    event.matches(OnBranches.create(configuration)),
                    event.matches(OnPaths.create(configuration))
            ));
        }

        if (EventTypeMatcher.matchType(key, "push")) {
            return validOnEmpty(configuration, () -> votes(
                    OnActivityType.create(configuration).map(event::matches).orElse(true),
                    event.matches(OnBranches.create(configuration)),
                    event.matches(OnTags.create(configuration)),
                    event.matches(OnPaths.create(configuration))
            ));
        }

        if (EventTypeMatcher.matchType(key, "schedule")) {
            return true;
        }

        if (EventTypeMatcher.matchType(key, "workflow_call")) {
            throw new UnsupportedOperationException();
        }

        if (EventTypeMatcher.matchType(key, "workflow_run")) {
            throw new UnsupportedOperationException();
        }

        if (EventTypeMatcher.matchType(key, "workflow_dispatch")) {
            return validOnEmpty(configuration, () -> votes(
                    OnWorkflowDispatchInputs.create(configuration).map(event::matches).orElse(true)
            ));
        }

        return validOnEmpty(configuration, () -> votes(
                OnActivityType.create(configuration).map(event::matches).orElse(true)
        ));
    }

    private static boolean validOnEmpty(JsonNode root, BooleanSupplier supplier) {
        if (root.isEmpty() || root.isNull()) {
            return true;
        }

        return supplier.getAsBoolean();
    }

    private static boolean votes(Boolean... votes) {
        return Arrays.stream(votes)
                .allMatch(e -> e.equals(true));
    }
}
