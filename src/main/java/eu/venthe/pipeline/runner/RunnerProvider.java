package eu.venthe.pipeline.runner;

import eu.venthe.pipeline.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.workflow.runs.WorkflowRunId;
import eu.venthe.pipeline.project.domain.ProjectId;

public interface RunnerProvider {
    boolean queueExecution(ProjectId projectId,
                           WorkflowRunId workflowRunId,
                           JobRunId executionId,
                           RunCallbackToken runCallbackToken,
                           RunnerDimensions dimensions);
}
