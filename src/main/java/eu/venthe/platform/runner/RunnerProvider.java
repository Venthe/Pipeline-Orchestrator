package eu.venthe.platform.runner;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.shared_kernel.project.ProjectId;

public interface RunnerProvider {
    boolean queueExecution(ProjectId projectId,
                           WorkflowRunId workflowRunId,
                           JobRunId executionId,
                           RunCallbackToken runCallbackToken,
                           RunnerDimensions dimensions);
}
