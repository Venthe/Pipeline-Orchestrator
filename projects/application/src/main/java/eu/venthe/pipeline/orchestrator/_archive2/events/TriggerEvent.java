package eu.venthe.pipeline.orchestrator._archive2.events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator._archive2.events.contexts.ActorContext;
import eu.venthe.pipeline.orchestrator._archive2.events.contexts.EventIdContext;
import eu.venthe.pipeline.orchestrator._archive2.events.contexts.TypeContext;
import eu.venthe.pipeline.orchestrator._archive2.events.model.Event;
import eu.venthe.pipeline.orchestrator._archive2.events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
import java.util.function.Function;

@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TriggerEvent implements Event {
    @ToString.Include
    protected final ObjectNode root;

    @EqualsAndHashCode.Include
    @Getter
    private final UUID id;

    @EqualsAndHashCode.Include
    @Getter
    private final EventType type;

    @Getter
    private final ActorContext actor;

    public TriggerEvent(ObjectNode root) {
        this.root = root;

        type = TypeContext.ensure(root);
        id = EventIdContext.ensure(root);
        actor = ActorContext.ensure(root.get("actor"));
    }

    public <T extends HandledEvent> T specify(Function<ObjectNode, T> creator) {
        return creator.apply(root);
    }

    @Override
    public ObjectNode eject() {
        return root;
    }
}
