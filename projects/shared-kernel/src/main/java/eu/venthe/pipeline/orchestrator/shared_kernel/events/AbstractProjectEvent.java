package eu.venthe.pipeline.orchestrator.shared_kernel.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AbstractProjectEvent implements ProjectEvent {
    @ToString.Include
    @Getter(AccessLevel.NONE)
    protected final ObjectNode root;

    @EqualsAndHashCode.Include
    private final UUID id;

    private final EventType type;
    private final OffsetDateTime timestamp;
    private final Optional<EnterpriseContext> enterprise;
    private final Optional<InstallationContext> installation;
    private final Optional<OrganizationContext> organization;
    private final RepositoryContext repository;
    /**
     * The GitHub user that triggered the event. This property is included in every webhook payload.
     */
    private final GithubUserContext sender;

    protected AbstractProjectEvent(ObjectNode root, EventType type, OffsetDateTime timestamp) {
        this.root = root;
        this.timestamp = timestamp;

        this.type = EventTypeContext.ensure(root.get("type"));
        this.id = EventIdContext.ensure(root.get("id"));
        this.enterprise = EnterpriseContext.create(root.get("enterprise"));
        this.installation = InstallationContext.create(root.get("installation"));
        this.organization = OrganizationContext.create(root.get("organization"));
        this.repository = RepositoryContext.ensure(root.get("repository"));
        this.sender = GithubUserContext.ensure(root.get("sender"));

        if (!getType().equals(type)) {
            throw new IllegalArgumentException(MessageFormat.format("Unsupported event type {0}. Expected {1}", getType(), type));
        }
    }

    @Override
    public ObjectNode getEvent() {
        return root;
    }

    @Override
    public <T extends ProjectEvent> T specify(Function<ObjectNode, T> creator) {
        return creator.apply(root);
    }
}
