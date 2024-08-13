package eu.venthe.pipeline.shared_kernel.system_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.shared_kernel.git.GitRevision;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.git.ReferenceTypeContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.git.RevisionContext;
import eu.venthe.pipeline.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * This event occurs when a Git branch or tag is deleted. To subscribe to all pushes to a repository, including branch
 * and tag deletions, use the push webhook event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class DeleteEvent extends AbstractProjectEvent {
    /**
     * The repository's current description.
     */
    private final Optional<String> description;
    /**
     * The name of the repository's default branch (usually main).
     */
    private final GitRevision mainBranch;
    private final GitRevision ref;
    private final String refType;

    public DeleteEvent(ObjectNode _root) {
        super(_root);

        var root = ContextUtilities.validateIsObjectNode(_root);

        final JsonNode description1 = root.get("description");
        description = ContextUtilities.Text.create(description1);
        mainBranch = RevisionContext.ensure(root.get("mainBranch"));
        ref = RevisionContext.ensure(root.get("ref"));
        refType = ReferenceTypeContext.ensure(root.get("refType"));
    }

    public EventType getType() {
        return EventType.DELETE;
    }
}
