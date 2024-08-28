package eu.venthe.platform.runner;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.workflow.runs.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.repository.domain.RepositoryName;

public interface RunnerProvider {
    boolean queueExecution(RepositoryName repositoryId,
                           WorkflowRunId workflowRunId,
                           JobRunId executionId,
                           RunCallbackToken runCallbackToken,
                           RunnerDimensions dimensions);
}
