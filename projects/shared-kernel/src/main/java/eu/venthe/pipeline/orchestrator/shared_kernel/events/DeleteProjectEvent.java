package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
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
public class DeleteProjectEvent extends AbstractProjectEvent {
    private final Optional<String> description;
    private final String masterBranch;
    private final String pusherType;
    private final String ref;
    private final RefTypeContext refType;

    protected DeleteProjectEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.DELETE, timestamp);

        description = DescriptionContext.create(root.get("description"));
        masterBranch = MasterBranchContext.ensure(root.get("master_branch"));
        pusherType = PusherTypeContext.ensure(root.get("pusher_type"));
        ref = RefContext.ensure(root.get("ref"));
        refType = RefTypeContext.ensure(root.get("ref_type"));
    }
}
