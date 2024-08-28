package eu.venthe.platform.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.ReferenceTypeContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.RevisionShortNameContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when a Git branch or tag is created.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class CreateEvent extends AbstractRepositoryEvent {
    /**
     * The repository's current description.
     */
    private final Optional<String> description;
    /**
     * The name of the repository's default branch (usually main).
     */
    private final RevisionShortName mainBranch;
    private final RevisionShortName ref;
    private final String refType;

    public CreateEvent(ObjectNode _root, TimeService timeService) {
        super(_root, timeService);

        var root = ContextUtilities.validateIsObjectNode(_root);

        final JsonNode description1 = root.get("description");
        description = ContextUtilities.Text.create(description1);
        mainBranch = RevisionShortNameContext.ensure(root.get("mainBranch"));
        // TODO: Full revision?
        ref = RevisionShortNameContext.ensure(root.get("ref"));
        refType = ReferenceTypeContext.ensure(root.get("refType"));
    }

    public EventType getType() {
        return EventType.CREATE;
    }
}
