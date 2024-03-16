package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator._archive2.utilities.ContextUtilities;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static com.google.common.collect.MoreCollectors.toOptional;
import static java.util.Arrays.stream;

@UtilityClass
public class ActionContext {
    public static Optional<Action> create(JsonNode root) {
        return ContextUtilities.toTextual(root).flatMap(Action::of);
    }

    public static Action ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Action must exist"));
    }

    @Getter
    @RequiredArgsConstructor
    public enum Action {
        OPENED("opened"),
        READY_FOR_REVIEW("ready_for_review"),
        CLOSED("closed");

        private final String value;

        public static Optional<Action> of(String type) {
            if (type == null) {
                return Optional.empty();
            }

            return stream(values())
                    .filter(action -> action.getValue().equalsIgnoreCase(type.trim()))
                    .collect(MoreCollectors.toOptional());
        }
    }
}
