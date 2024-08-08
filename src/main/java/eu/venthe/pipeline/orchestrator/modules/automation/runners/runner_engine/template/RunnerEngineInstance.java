package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

import java.net.URL;

/**
 * Configured instance of the engine.
 */
public interface RunnerEngineInstance {
    /**
     * Schedules execution the given code on the runner
     *
     * @return Did the execution enqueue action happen successfully.
     */
    // TODO: Add generic method to pick a runner based on the dimensions
    // TODO: Add generic method to get the JobExecution to be executed per runner
    boolean queueExecution(ProjectId projectId,
                           JobRunId executionId,
                           URL systemApiUrl,
                           ExecutionCallbackToken executionCallbackToken,
                           RunnerDimensions dimensions);

    // TODO: Add generic runner handling code in the abstract class
    RunnerId registerRunner(RunnerDimensions dimensions);
}
