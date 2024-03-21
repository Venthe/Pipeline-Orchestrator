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
 * This event occurs when a Git branch or tag is created.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Contents" repository permission.
 * <p>
 * Notes:
 *   - This event will not occur when more than three tags are created at once.
 *   - Payloads are capped at 25 MB. If an event generates a larger payload, GitHub will not deliver a payload for that
 *     webhook event. This may happen, for example, if many branches or tags are pushed at once. We suggest monitoring
 *     your payload size to ensure delivery.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CreateProjectEvent extends AbstractProjectEvent {
    private final Optional<String> description;
    private final String masterBranch;
    private final String pusherType;
    private final String ref;
    private final RefTypeContext refType;

    protected CreateProjectEvent(ObjectNode root, ZonedDateTime timestamp) {
        super(root, EventType.CREATE, timestamp);

        description = DescriptionContext.create(root.get("description"));
        masterBranch = MasterBranchContext.ensure(root.get("master_branch"));
        pusherType = PusherTypeContext.ensure(root.get("pusher_type"));
        ref = RefContext.ensure(root.get("ref"));
        refType = RefTypeContext.ensure(root.get("ref_type"));
    }
}
