package eu.venthe.pipeline.orchestrator.projects.domain.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.List.of;

class GraphUtilityTest {
    public static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory());

    @Test
    void test1() {
        // given
        var jobs = parseYaml("""
                d:
                 needs:
                   - a
                   - b
                a: {}
                b:
                  needs: a
                c:
                  needs: a
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then
        Assertions.assertThat(result).isEqualTo(of(of("a"), of("b", "c"), of("d")));
    }

    @Test
    void test2() {
        // given
        var jobs = parseYaml("""
                a: {}
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then

        Assertions.assertThat(result).isEqualTo(of(of("a")));
    }

    @Test
    void test3() {
        // given
        var jobs = parseYaml("""
                d:
                 needs:
                   - a
                a: {}
                b:
                  needs: a
                c:
                  needs: a
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then
        Assertions.assertThat(result).isEqualTo(of(of("a"), of("b", "c", "d")));
    }

    @Test
    void test4() {
        // given
        var jobs = parseYaml("""
                d:
                 needs:
                   - a
                a: {}
                b:
                  needs:
                    - a
                    - d
                c:
                  needs:
                    - a
                    - d
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then
        Assertions.assertThat(result).isEqualTo(of(of("a"), of("d"), of("b", "c")));
    }

    @Test
    void test5() {
        // given
        var jobs = parseYaml("""
                d:
                 needs:
                   - a
                a: {}
                b:
                  needs:
                    - d
                c:
                  needs:
                    - b
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then
        Assertions.assertThat(result).isEqualTo(of(of("a"), of("d"), of("b"), of("c")));
    }

    @Test
    void test6() {
        // given
        var jobs = parseYaml("""
                a: {}
                b:
                  needs:
                    - a
                c:
                  needs:
                    - b
                d:
                  needs:
                    - a
                e:
                  needs:
                    - a
                f:
                  needs:
                    - a
                    - d
                """);

        // when
        var result = GraphUtility.buildDependencyTree(jobs);

        // then
        Assertions.assertThat(result).isEqualTo(of(of("a"), of("b", "d", "e"), of("c", "f")));
    }

    @SneakyThrows
    public static Set<GraphUtility.JobRequirements> parseYaml(String value) {
        ObjectNode nodes = (ObjectNode) YAML_MAPPER.readTree(value);

        return nodes.properties().stream()
                .map(e2 -> Map.entry(
                        e2.getKey(),
                        Optional.ofNullable(e2.getValue().get("needs")).map(e -> e.isArray()
                                ? StreamSupport.stream(Spliterators.spliteratorUnknownSize(e.elements(), Spliterator.ORDERED), false).map(JsonNode::asText).collect(Collectors.toSet())
                                : Set.of(e.asText())).orElse(Collections.emptySet()))
                )
                .map(e -> new GraphUtility.JobRequirements(e.getKey(), e.getValue()))
                .collect(Collectors.toSet());
    }
}
