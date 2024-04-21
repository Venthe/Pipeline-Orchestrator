package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The steps context contains information about the steps in the current job that have an id specified and have already
 * run.
 * <p>
 * This context changes for each step in a job. You can access this context from any step in a job. This object contains
 * all the properties listed below.
 */
@AllArgsConstructor
public class StepsContext {
    public static Map<String, StepContext> ensure(JsonNode steps) {
        throw new UnsupportedOperationException();
    }

    @AllArgsConstructor
    public static class StepContext {
        /**
         * The set of outputs defined for the step. For more information, see "Metadata syntax for GitHub Actions."
         */
        private final Map<String, String> outputs;
        /**
         * The result of a completed step after continue-on-error is applied. Possible values are success, failure,
         * cancelled, or skipped. When a continue-on-error step fails, the outcome is failure, but the final conclusion
         * is success.
         */
        private final String conclusion;
        /**
         * The result of a completed step before continue-on-error is applied. Possible values are success, failure,
         * cancelled, or skipped. When a continue-on-error step fails, the outcome is failure, but the final conclusion
         * is success.
         */
        private final String outcome;
    }
}
