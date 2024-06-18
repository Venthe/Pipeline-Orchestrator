package eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.context.jobs.context;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Optional;

// TODO: Implement context
public class TimeoutContext {
    public static Optional<Integer> create(JsonNode timeoutMinutes) {
        throw new UnsupportedOperationException();
    }
}
