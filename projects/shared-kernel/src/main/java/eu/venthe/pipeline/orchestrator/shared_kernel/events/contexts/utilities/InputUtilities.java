package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class InputUtilities {
    public static InputValue<?> describe(JsonNode node) {
        if (node.isTextual()) {
            return InputValue.ofString(node.textValue());
        } else if (node.isNumber()) {
            return InputValue.ofNumber(node.decimalValue());
        } else if (node.isBoolean()) {
            return InputValue.ofBoolean(node.booleanValue());
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Value
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class InputValue<T> {
        T value;
        Type type;

        public String serialize() {
            return value.toString();
        }

        public static InputValue<String> ofString(String value) {
            return new InputValue<>(value, Type.STRING);
        }

        public static InputValue<BigDecimal> ofNumber(BigDecimal value) {
            return new InputValue<>(value, Type.NUMBER);
        }

        public static InputValue<Boolean> ofBoolean(Boolean value) {
            return new InputValue<>(value, Type.BOOLEAN);
        }

        public enum Type {
            STRING,
            BOOLEAN,
            NUMBER
        }
    }
}
