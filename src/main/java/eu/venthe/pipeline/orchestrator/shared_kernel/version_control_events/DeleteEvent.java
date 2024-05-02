package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.git.ReferenceContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.git.ReferenceTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.model.EventType;
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
    private final String mainBranch;
    private final String ref;
    private final String refType;

    public DeleteEvent(ObjectNode root) {
        super(root, EventType.DELETE);

        final JsonNode description1 = root.get("description");
        description = ContextUtilities.Text.create(description1);
        mainBranch = ReferenceContext.ensure(root.get("mainBranch"));
        ref = ReferenceContext.ensure(root.get("ref"));
        refType = ReferenceTypeContext.ensure(root.get("refType"));
    }
}
