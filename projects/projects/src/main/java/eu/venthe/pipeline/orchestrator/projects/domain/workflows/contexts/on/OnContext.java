package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.orchestrator.projects.domain.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.model.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.MoreCollectors.toOptional;
import static eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities.iteratorToStream;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class OnContext {
    private final JsonNode root;

    public static Optional<OnContext> create(JsonNode on) {
        return ofNullable(on).map(OnContext::new);
    }

    public static OnContext ensure(JsonNode on) {
        return create(on).orElseThrow(() -> new IllegalArgumentException("There is no \"on\" property, this workflow will never run"));
    }

    public Boolean on(HandledEvent event) {
        if (event == null) throw new IllegalArgumentException("Input should not be null");

        if (!root.isTextual() && root.isEmpty()) {
            log.error("Event not matched due to lack of \"on\" key in the workflow {}", root);
            return false;
        }
        if (root.isTextual()) {
            log.info("Matching single event {}", root);
            return singleEvent(root.asText(), event.getType());
        }
        if (root.isArray()) {
            log.info("Matching array of events {}", root);
            Collection<JsonNode> nodes = iteratorToStream(root.elements()).collect(Collectors.toSet());

            int size = nodes.stream().filter(e -> !e.isTextual()).collect(Collectors.toSet()).size();
            if (size > 0) throw new UnsupportedOperationException();

            return nodes.stream()
                    .anyMatch(el -> singleEvent(el.asText(), event.getType()));
        }
        if (root.isObject()) {
            if (root.isEmpty()) {
                return true;
            }

            Optional<JsonNode> node = root.properties().stream()
                    .filter(e -> EventType.of(e.getKey()).map(w -> event.getType().equals(w)).orElse(false))
                    .map(Map.Entry::getValue)
                    .collect(MoreCollectors.toOptional());

            log.info("Matching entry from object {}, matched node {}", root, node);

            if (node.isEmpty()) {
                return false;
            }

            if (node.get().isNull()) {
                return true;
            }

            ObjectNode onNode = (ObjectNode) node.orElseThrow();
            List<Boolean> votes = new ArrayList<>();

            OnTypes.create(onNode).ifPresent(types -> votes.add(event.matches(types)));
            OnInputs.create(onNode).ifPresent(inputs -> votes.add(event.matches(inputs)));
            votes.add(event.matches(OnPaths.create(onNode)));
            votes.add(event.matches(OnBranches.create(onNode)));
            votes.add(event.matches(OnTags.create(onNode)));

            return votes.stream()
                    .allMatch(e -> e.equals(true));
            // TODO: Add on handlers
            //  on.push.<tags|tags-ignore>
            //  on.<push|pull_request|pull_request_target>.<paths|paths-ignore>
            //  on.schedule
            //  on.workflow_call
            //  on.workflow_call.inputs
            //  on.workflow_call.inputs.<input_id>.type
            //  on.workflow_call.outputs
            //  on.workflow_call.secrets
            //  on.workflow_call.secrets.<secret_id>
            //  on.workflow_call.secrets.<secret_id>.required
        }

        throw new UnsupportedOperationException();
    }

    private Boolean singleEvent(String node, EventType type) {
        return EventType.of(node)
                .map(type::equals)
                .orElse(false);
    }
}
