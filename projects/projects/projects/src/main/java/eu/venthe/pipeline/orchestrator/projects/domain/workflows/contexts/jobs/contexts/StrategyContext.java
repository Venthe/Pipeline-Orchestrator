package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.ContextUtilities;
import lombok.Getter;

import java.util.Optional;

/**
 * Use jobs.<job_id>.strategy to use a matrix strategy for your jobs. A matrix strategy lets you use variables in a
 * single job definition to automatically create multiple job runs that are based on the combinations of the variables.
 * For example, you can use a matrix strategy to test your code in multiple versions of a language or on multiple
 * operating systems. For more information, see "Using a matrix for your jobs."
 */
public class StrategyContext {
    private final ObjectNode root;
    private final MatrixContext matrix;

    @Getter
    private final Boolean failFast;

    @Getter
    private final Integer maxParallel;

    private StrategyContext(ObjectNode root) {
        this.root = root;

        matrix = MatrixContext.ensure(this.root.get("matrix"));

        this.failFast = Optional.ofNullable(root.get("failFast"))
                .filter(JsonNode::isBoolean)
                .map(JsonNode::asBoolean)
                .orElse(false);
        this.maxParallel = Optional.ofNullable(root.get("maxParallel"))
                .filter(JsonNode::isNumber)
                .map(JsonNode::asInt)
                .orElse(256);
    }

    public static Optional<StrategyContext> create(JsonNode root) {
        return ContextUtilities.get(StrategyContext::new, root);
    }

    public static StrategyContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Strategy must be present"));
    }

}
