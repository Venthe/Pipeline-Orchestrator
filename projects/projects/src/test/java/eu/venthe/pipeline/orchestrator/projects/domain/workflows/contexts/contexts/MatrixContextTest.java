package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.contexts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.ThrowingCallable;

class MatrixContextTest {
    private static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    @ValueSource(strings = {"{}", "~", "null"})
    @ParameterizedTest
    void invalidValue(String value) {
        ThrowingCallable throwingCallable = () -> MatrixContext.ensure(parse("""
                version: %s
                """.formatted(value)));

        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Each value should be an array");
    }

    private static JsonNode parse(String data) throws JsonProcessingException {
        return objectMapper.readTree(data);
    }

    @ValueSource(strings = {"{}", "[]"})
    @ParameterizedTest
    void invalidValues(String value) {
        ThrowingCallable throwingCallable = () -> MatrixContext.ensure(parse("""
                version: [%s]
                """.formatted(value)));

        assertThatThrownBy(throwingCallable)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Each value in array should be of simple type");
    }

    @SneakyThrows
    @Test
    void singleValue() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["asdf"]
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactly(strategy(string("version", "asdf")));
    }

    @SneakyThrows
    @Test
    void valuesWithDifferentTypes() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["asdf", 123, true]
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy(string("version", "asdf")),
                        strategy(number("version", 123)),
                        strategy(bool("version", true))
                );
    }

    @SneakyThrows
    @Test
    void includes() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["asdf", 123, true]
                include:
                  - stuff: "123"
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy(string("version", "asdf"), string("stuff", "123")),
                        strategy(number("version", 123), string("stuff", "123")),
                        strategy(bool("version", true), string("stuff", "123"))
                );
    }

    @SneakyThrows
    @Test
    void includesComplex() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                fruit: [apple, pear]
                animal: [cat, dog]
                include:
                  - color: green
                  - color: pink
                    animal: cat
                  - fruit: apple
                    shape: circle
                  - fruit: banana
                  - fruit: banana
                    animal: cat
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy("{fruit: apple, animal: cat, color: pink, shape: circle}"),
                        strategy("{fruit: apple, animal: dog, color: green, shape: circle}"),
                        strategy("{fruit: pear, animal: cat, color: pink}"),
                        strategy("{fruit: pear, animal: dog, color: green}"),
                        strategy("{fruit: banana}"),
                        strategy("{fruit: banana, animal: cat}")
                );
    }

    @SneakyThrows
    @Test
    void excludes() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                os: [macos-latest, windows-latest]
                version: [12, 14, 16]
                environment: [staging, production]
                exclude:
                  - os: macos-latest
                    version: 12
                    environment: production
                  - os: windows-latest
                    version: 16
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy("{version: 12, os: windows-latest, environment: production}"),
                        strategy("{version: 14, os: macos-latest, environment: staging}"),
                        strategy("{version: 12, os: macos-latest, environment: staging}"),
                        strategy("{version: 14, os: windows-latest, environment: production}"),
                        strategy("{version: 16, os: macos-latest, environment: staging}"),
                        strategy("{version: 12, os: windows-latest, environment: staging}"),
                        strategy("{version: 14, os: macos-latest, environment: production}"),
                        strategy("{version: 14, os: windows-latest, environment: staging}"),
                        strategy("{version: 16, os: macos-latest, environment: production}")
                );
    }

    @SneakyThrows
    @Test
    void simpleMatrix() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["1"]
                color: ["red"]
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy(string("version", "1"), string("color", "red"))
                );
    }

    @SneakyThrows
    @Test
    void moderateMatrix() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["1", "2"]
                color: ["red", "green"]
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy(string("version", "1"), string("color", "red")),
                        strategy(string("version", "1"), string("color", "green")),
                        strategy(string("version", "2"), string("color", "red")),
                        strategy(string("version", "2"), string("color", "green"))
                );
    }

    @SneakyThrows
    @Test
    void complexMatrix() {
        MatrixContext ensure = MatrixContext.ensure(parse("""
                version: ["1", "2"]
                color: ["red", "green"]
                smell: ["rose", "lemon"]
                """));

        Set<ObjectNode> result = ensure.getMatrix(objectMapper);

        assertThat(result)
                .containsExactlyInAnyOrder(
                        strategy(string("version", "1"), string("color", "red"), string("smell", "rose")),
                        strategy(string("version", "1"), string("color", "red"), string("smell", "lemon")),
                        strategy(string("version", "1"), string("color", "green"), string("smell", "rose")),
                        strategy(string("version", "1"), string("color", "green"), string("smell", "lemon")),
                        strategy(string("version", "2"), string("color", "red"), string("smell", "rose")),
                        strategy(string("version", "2"), string("color", "red"), string("smell", "lemon")),
                        strategy(string("version", "2"), string("color", "green"), string("smell", "rose")),
                        strategy(string("version", "2"), string("color", "green"), string("smell", "lemon"))
                );
    }

    @SafeVarargs
    final ObjectNode strategy(Map.Entry<String, JsonNode>... nodes) {
        ObjectNode root = objectMapper.createObjectNode();
        Arrays.stream(nodes).forEach(e -> root.set(e.getKey(), e.getValue()));
        return root;
    }

    final ObjectNode strategy(String... nodes) {
        ObjectNode root = objectMapper.createObjectNode();
        Arrays.stream(nodes)
                .map(content -> {
                    try {
                        return objectMapper.readTree(content);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(e -> e.properties().stream())
                .forEach(e -> root.set(e.getKey(), e.getValue()));
        return root;
    }

    Map.Entry<String, JsonNode> string(String key, String value) {
        return new AbstractMap.SimpleEntry<>(key, objectMapper.getNodeFactory().textNode(value));
    }

    Map.Entry<String, JsonNode> number(String key, Integer value) {
        return new AbstractMap.SimpleEntry<>(key, objectMapper.getNodeFactory().numberNode(value));
    }

    Map.Entry<String, JsonNode> bool(String key, Boolean value) {
        return new AbstractMap.SimpleEntry<>(key, objectMapper.getNodeFactory().booleanNode(value));
    }
}
