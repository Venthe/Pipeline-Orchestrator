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
 * This event occurs when a Git branch or tag is created.
 * <p>
 * To subscribe to this event, a GitHub App must have at least read-level access for the "Contents" repository permission.
 * <p>
 * Notes:
 * - This event will not occur when more than three tags are created at once.
 * - Payloads are capped at 25 MB. If an event generates a larger payload, GitHub will not deliver a payload for that
 * webhook event. This may happen, for example, if many branches or tags are pushed at once. We suggest monitoring
 * your payload size to ensure delivery.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CreateEvent extends AbstractProjectEvent {
    private final Optional<String> description;
    private final String masterBranch;
    private final String pusherType;
    private final Reference.Name ref;
    private final ProjectRefTypeContext refType;

    protected CreateEvent(ObjectNode root, OffsetDateTime timestamp) {
        super(root, EventType.CREATE, timestamp);

        description = ProjectDescriptionContext.create(root.get("description"));
        masterBranch = ProjectHeadBranchContext.ensure(root.get("master_branch"));
        pusherType = ProjectPusherTypeContext.ensure(root.get("pusher_type"));
        ref = GitReferenceNameContext.ensure(root.get("ref"));
        refType = ProjectRefTypeContext.ensure(root.get("ref_type"));
    }
}
