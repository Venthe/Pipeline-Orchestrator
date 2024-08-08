package eu.venthe.pipeline.orchestrator.modules.automation.runners.model;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
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
        runnerEngineInstance.queueExecution(projectId,
                workflowRunId,
                executionId,
                systemApiUrl,
                runCallbackToken,
                dimensions);
    }

    public RunnerId registerRunner(RunnerDimensions runnerDimensions) {
        return runnerEngineInstance.registerRunner(runnerDimensions);
    }
}
