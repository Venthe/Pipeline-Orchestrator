package eu.venthe.pipeline.orchestrator.workflow_executions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.contexts.InputsContext;

import java.util.Optional;

public class JobData {
    private final ObjectNode root;

    private final Optional<InputsContext> inputs;
//    private final ObjectNode env;

    public JobData(JsonNode root) {
        if (root == null || !root.isObject() ||root.isNull()) {
            throw new IllegalArgumentException();
        }

        this.root = (ObjectNode) root;

        inputs = InputsContext.create(this.root);
    }
//
//    InternalSnapshot internal();
//    Optional<JobSnapshot> job();
//    StrategySnapshot strategy();
//    Optional<MatrixSnapshot> matrix();
//    Optional<NeedsSnapshot> needs();
}
