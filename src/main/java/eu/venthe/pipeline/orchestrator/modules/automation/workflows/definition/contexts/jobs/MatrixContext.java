package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Sets;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

// TODO: Implement context
/**
 * Use jobs.<job_id>.strategy.matrix to define a matrix of different job configurations. Within your matrix, define
 * one or more variables followed by an array of values. For example, the following matrix has a variable called
 * version with the value [10, 12, 14] and a variable called os with the value [ubuntu-latest, windows-latest]:
 * <p>
 * A job will run for each possible combination of the variables. In this example, the workflow will run six jobs,
 * one for each combination of the os and version variables.
 * <p>
 * By default, GitHub will maximize the number of jobs run in parallel depending on runner availability. The order
 * of the variables in the matrix determines the order in which the jobs are created. The first variable you define
 * will be the first job that is created in your workflow run. For example, the above matrix will create the jobs in
 * the following order:
 */
public class MatrixContext {
    private final ObjectNode root;

    private final Map<String, JsonNode> properties;

    private final Set<ObjectNode> include;

    private final Set<ObjectNode> exclude;

    private MatrixContext(JsonNode _root) {
        this.root = ContextUtilities.validateIsObjectNode(_root);

        this.properties = of(this.root)
                .map(r -> r.properties().stream()
                        .filter(e -> !Set.of("include", "exclude").contains(e.getKey()))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue))
                )
                .orElse(emptyMap());
        this.properties.values().forEach(property -> {
            if (!property.isArray()) {
                throw new IllegalArgumentException("Each value should be an array");
            }

            property.forEach(MatrixContext::validateSimpleType);
        });
        include = createProperty("include");
        exclude = createProperty("exclude");
    }

    private static void validateSimpleType(JsonNode node) {
        if (!node.isNumber() && !node.isTextual() && !node.isBoolean()) {
            throw new IllegalArgumentException("Each value in array should be of simple type");
        }
    }

    private Set<ObjectNode> createProperty(String propertyName) {
        JsonNode node = this.root.get(propertyName);
        if (node != null && !node.isArray()) {
            throw new UnsupportedOperationException();
        }
        return ofNullable((ArrayNode) node)
                .map(n -> StreamSupport.stream(n.spliterator(), false)
                        .map(property -> {
                            if (!property.isObject()) {
                                throw new IllegalArgumentException("Node must be an object");
                            }

                            StreamSupport.stream(property.spliterator(), false).forEach(MatrixContext::validateSimpleType);

                            return property;
                        })
                        .map(ObjectNode.class::cast)
                        .collect(toSet()))
                .orElse(emptySet());
    }

    public static Optional<MatrixContext> create(JsonNode root) {
        return ContextUtilities.create(root, MatrixContext::new);
    }

    public static MatrixContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Matrix strategy must be present"));
    }

    public Set<ObjectNode> getMatrix(ObjectMapper objectMapper) {
        Set<ObjectNode> originalMatrixCombinations = computeMatrix(objectMapper, properties.entrySet().iterator(), new HashSet<>()).stream()
                .filter(filterExclusions(exclude))
                .collect(toSet());

        Map<ObjectNode, ObjectNode> modifiedMatrixCombinations = new HashMap<>();

        Set<ObjectNode> additionalMatrixCombinations = new HashSet<>();

        include.forEach(propsToBeIncluded -> originalMatrixCombinations.forEach(originalCombination -> {
            var modifiedCombination = modifiedMatrixCombinations.getOrDefault(originalCombination, originalCombination.deepCopy()).deepCopy();
            boolean isModified = false;

            Set<String> originalKeys = originalCombination.properties().stream().map(Map.Entry::getKey).collect(toSet());
            Set<String> includedKeys = propsToBeIncluded.properties().stream().map(Map.Entry::getKey).collect(toSet());

            Set<String> newKeys = Sets.difference(includedKeys, originalKeys);
            Set<String> intersection = Sets.intersection(originalKeys, includedKeys);

            boolean allIntersectingValuesMatch = intersection.stream().allMatch(i -> originalCombination.get(i).equals(propsToBeIncluded.get(i)));
            boolean anyIntersectingValuesMatch = intersection.stream().anyMatch(i -> originalCombination.get(i).equals(propsToBeIncluded.get(i)));
            boolean noneIntersectingValuesMatch = intersection.stream().noneMatch(i -> originalCombination.get(i).equals(propsToBeIncluded.get(i)));

            if (newKeys.isEmpty() && noneIntersectingValuesMatch) {
                additionalMatrixCombinations.add(propsToBeIncluded);
            } else if (!allIntersectingValuesMatch && anyIntersectingValuesMatch) {
                additionalMatrixCombinations.add(propsToBeIncluded);
            } else if (allIntersectingValuesMatch) {
                for (Map.Entry<String, JsonNode> property : propsToBeIncluded.properties()) {
                    if (originalCombination.get(property.getKey()) == null) {
                        modifiedCombination.set(property.getKey(), property.getValue());
                        isModified = true;
                    }
                }

                if (isModified) {
                    modifiedMatrixCombinations.put(originalCombination, modifiedCombination);
                }
            }
        }));

        // Remove all that were  modified from the original
        modifiedMatrixCombinations.forEach((key, value) -> originalMatrixCombinations.remove(key));

        return Stream.concat(
                Stream.concat(
                        modifiedMatrixCombinations.values().stream(),
                        originalMatrixCombinations.stream()
                ),
                additionalMatrixCombinations.stream()
        ).collect(toSet());
    }

    private static Predicate<ObjectNode> filterExclusions(Set<ObjectNode> exclusions) {
        return omc -> exclusions.stream().noneMatch(exc -> {
            Set<Map.Entry<String, JsonNode>> exclusionProps = exc.properties();
            Set<Map.Entry<String, JsonNode>> combinations = omc.properties();
            return Sets.difference(exclusionProps, combinations).isEmpty();
        });
    }

    private static Set<ObjectNode> computeMatrix(ObjectMapper objectMapper, Iterator<Map.Entry<String, JsonNode>> iter, Set<ObjectNode> result) {
        if (!iter.hasNext()) {
            return result;
        }

        Set<Map.Entry<String, JsonNode>> current = toArray(iter.next());
        UnaryOperator<Set<ObjectNode>> compute = action -> computeMatrix(objectMapper, iter, action);

        if (result.isEmpty()) {
            return compute.apply(current.stream()
                    .map(entryToObjectNode(objectMapper))
                    .collect(toSet())
            );
        }

        return compute.apply(result.stream()
                .flatMap(value -> current.stream()
                        .map(entryToObjectNode(objectMapper))
                        .map(enrichNode(value))
                )
                .collect(toSet()));
    }

    private static Set<Map.Entry<String, JsonNode>> toArray(Map.Entry<String, JsonNode> first) {
        String key = first.getKey();
        return StreamSupport.stream(first.getValue().spliterator(), false).map(e -> Map.entry(key, e)).collect(toSet());
    }

    private static Function<Map.Entry<String, JsonNode>, ObjectNode> entryToObjectNode(ObjectMapper objectMapper) {
        return entry -> {
            ObjectNode objectNode = objectMapper.getNodeFactory().objectNode();
            objectNode.set(entry.getKey(), entry.getValue());
            return objectNode;
        };
    }

    private static Function<ObjectNode, ObjectNode> enrichNode(ObjectNode v1) {
        return objectNode -> {
            v1.properties().forEach(p -> objectNode.set(p.getKey(), p.getValue()));
            return objectNode;
        };
    }
}
