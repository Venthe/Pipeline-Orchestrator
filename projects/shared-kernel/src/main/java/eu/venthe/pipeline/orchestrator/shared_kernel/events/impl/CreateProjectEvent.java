package eu.venthe.pipeline.orchestrator.shared_kernel.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import lombok.Getter;

import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities.toText;

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
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class CreateProjectEvent extends AbstractProjectEvent {

    private final Optional<EnterpriseContext> enterprise;
    private final Optional<InstallationContext> installation;
    private final Optional<OrganizationContext> organization;
    private final RepositoryContext repository;
    /**
     * The GitHub user that triggered the event. This property is included in every webhook payload.
     */
    private final GithubUserContext sender;
    /**
     * description: The repository's current description.
     */
    private final Optional<String> description;
    /**
     * The name of the repository's default branch (usually main).
     */
    private final String masterBranch;
    /**
     * The pusher type for the event. Can be either user or a deploy key.
     */
    private final String pusherType;
    /**
     * The git ref resource.
     */
    private final String ref;
    private final RefTypeContext refType;

    protected CreateProjectEvent(ObjectNode root) {
        super(root, EventType.CREATE);

        enterprise = EnterpriseContext.create(root.get("enterprise"));
        installation = InstallationContext.create(root.get("installation"));
        organization = OrganizationContext.create(root.get("organization"));
        repository = RepositoryContext.ensure(root.get("repository"));
        sender = GithubUserContext.ensure(root.get("sender"));
        description = ContextUtilities.create(root.get("description"), toText());
        masterBranch = ContextUtilities.ensure(root.get("master_branch"), toText());
        pusherType = ContextUtilities.ensure(root.get("pusher_type"), toText());
        ref = ContextUtilities.ensure(root.get("ref"), toText());
        refType = RefTypeContext.ensure(root.get("ref_type"));
    }
}
