package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ProjectDescriptionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ReferenceContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.git.ReferenceTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
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

        description = ProjectDescriptionContext.create(root.get("description"));
        mainBranch = ReferenceContext.ensure(root.get("mainBranch"));
        ref = ReferenceContext.ensure(root.get("ref"));
        refType = ReferenceTypeContext.ensure(root.get("refType"));
    }
}
