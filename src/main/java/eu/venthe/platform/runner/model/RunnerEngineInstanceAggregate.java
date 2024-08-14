package eu.venthe.platform.runner.model;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerContext;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerId;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;

@RequiredArgsConstructor
@Value
public class RunnerEngineInstanceAggregate implements Aggregate<RunnerEngineInstanceId> {
    RunnerEngineInstanceId id;
    RunnerEngineInstance runnerEngineInstance;

    // TODO: Add logs
    public void queueJobExecution(ProjectId projectId,
                                  WorkflowRunId workflowRunId,
                                  JobRunId executionId,
                                  URL systemApiUrl,
                                  RunCallbackToken runCallbackToken,
                                  RunnerDimensions dimensions) {
        var context = new RunnerContext(null, systemApiUrl, runCallbackToken);
        runnerEngineInstance.queueExecution(context,
                dimensions);
    }

    public RunnerId registerRunner(RunnerDimensions runnerDimensions) {
        return runnerEngineInstance.registerRunner(runnerDimensions);
    }
}
