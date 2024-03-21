package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ProjectDescriptionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ProjectHeadBranchContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ProjectPusherTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ProjectRefTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.GitReferenceNameContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.git.Reference;
import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * This event occurs when a Git branch or tag is deleted. To subscribe to all pushes to a repository, including branch and tag deletions, use the push webhook event.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Contents" repository permission.
 * <p>
 * Note: This event will not occur when more than three tags are deleted at once.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DeleteEvent extends AbstractProjectEvent {
    private final Optional<String> description;
    private final String masterBranch;
    private final String pusherType;
    private final Reference.Name ref;
    private final ProjectRefTypeContext refType;

    protected DeleteEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.DELETE, timestamp);

        description = ProjectDescriptionContext.create(root.get("description"));
        masterBranch = ProjectHeadBranchContext.ensure(root.get("master_branch"));
        pusherType = ProjectPusherTypeContext.ensure(root.get("pusher_type"));
        ref = GitReferenceNameContext.ensure(root.get("ref"));
        refType = ProjectRefTypeContext.ensure(root.get("ref_type"));
    }
}
