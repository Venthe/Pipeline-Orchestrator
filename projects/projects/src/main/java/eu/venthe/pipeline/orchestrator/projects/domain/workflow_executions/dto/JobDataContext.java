package eu.venthe.pipeline.orchestrator.projects.domain.workflow_executions.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.ContextUtilities;

import java.util.Optional;

public class JobDataContext {
    private final ObjectNode root;

    private final InternalContext internal;
    private final VarsContest vars;
    private final EnvContext env;
    private final JobContext job;
    private final Optional<JobsContext> jobs;
    private final Optional<StrategyContext> strategy;
    private final MatrixContext matrix;
    private final NeedsContext needs;
    private final Optional<InputsContext> inputs;

    public JobDataContext(ObjectMapper objectMapper) {
        this.root = objectMapper.createObjectNode();

        this.internal = InternalContext.ensure(root.get("internal"));
        this.env = EnvContext.ensure(root.get("env"));
        this.vars = VarsContest.ensure(root.get("vars"));
        this.job = JobContext.ensure(root.get("job"));
        this.jobs = JobsContext.create(root.get("jobs"));
        this.strategy = StrategyContext.create(root.get("strategy"));
        this.matrix = MatrixContext.ensure(Optional.ofNullable(root.get("matrix")).orElseGet(objectMapper::createObjectNode));
        this.needs = NeedsContext.ensure(Optional.ofNullable(root.get("needs")).orElseGet(objectMapper::createObjectNode));
        this.inputs = InputsContext.create(root.get("inputs"));
    }

    public static class InternalContext {
        public static InternalContext ensure(JsonNode internal) {
            return null;
        }
    }
    public static class EnvContext {
        public static EnvContext ensure(JsonNode internal) {
            return null;
        }
    }
    public static class VarsContest {
        public static VarsContest ensure(JsonNode vars) {
            return null;
        }
    }
    public static class JobContext {
        public static JobContext ensure(JsonNode job) {
            return null;
        }
    }
    public static class JobsContext {
        public static Optional<JobsContext> create(JsonNode jobs) {
            return null;
        }
    }
    public static class StrategyContext {
        public static Optional<StrategyContext> create(JsonNode job) {
            return null;
        }
    }
    public static class MatrixContext {
        public static MatrixContext ensure(JsonNode matrix) {
            return null;
        }
    }
    public static class NeedsContext {
        private final ObjectNode root;

        protected NeedsContext(JsonNode root) {
            if (root == null || root.isNull()) throw new IllegalArgumentException();
            if (!root.isObject()) throw new IllegalArgumentException();

            this.root = (ObjectNode) root;
        }

        public static Optional<NeedsContext> create(JsonNode root) {
            return ContextUtilities.get(NeedsContext::new, root);
        }

        public static NeedsContext ensure(JsonNode root) {
            return create(root).orElseThrow(() -> new IllegalArgumentException("Needs must be present"));
        }
    }
    public static class InputsContext {
        public static Optional<InputsContext> create(JsonNode inputs) {
            return null;
        }
    }
}
