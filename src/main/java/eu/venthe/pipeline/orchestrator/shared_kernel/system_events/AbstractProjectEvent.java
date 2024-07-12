package eu.venthe.pipeline.orchestrator.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.model.EventTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.text.MessageFormat;

// TODO: Add optional enterprise context
// TODO: Add optional installation context
// TODO: Add optional organization context
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@SuperBuilder
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract class AbstractProjectEvent implements SystemEvent, ProjectEvent {
    @EqualsAndHashCode.Include
    private final EventId id;

    @NonNull
    private final RepositoryContext repository;
    /**
     * The user that triggered the event.
     */
    private final UserContext sender;

    protected AbstractProjectEvent(JsonNode _root) {
        var root = ContextUtilities.validateIsObjectNode(_root);

        this.id = EventIdContext.ensure(root.get("id"));
        this.repository = RepositoryContext.ensure(root.get("repository"));
        this.sender = UserContext.ensure(root.get("sender"));

        if (!getType().equals(EventTypeContext.ensure(root.get("type")))) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported event type {0}. Expected {1}", getType(), EventTypeContext.ensure(root.get("type"))));
        }
    }
}
