package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class JobsContext {
    /**
     * The result of a job in the reusable workflow. Possible values are success, failure, cancelled, or skipped.
     */
    private final String result;
    /**
     * The set of outputs of a job in a reusable workflow.
     */
    private final Map<String, String> outputs;

    public static Map<String, JobsContext> ensure(JsonNode jobs) {
        throw new UnsupportedOperationException();
    }
}
