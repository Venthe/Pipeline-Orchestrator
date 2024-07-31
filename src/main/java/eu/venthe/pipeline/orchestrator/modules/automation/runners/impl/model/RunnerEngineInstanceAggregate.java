package eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
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

    public void queueJobExecution(ProjectId projectId, JobExecutionId executionId, URL systemApiUrl, ExecutionCallbackToken executionCallbackToken, Dimension... dimensions) {
        runnerEngineInstance.queueExecution(projectId, executionId, systemApiUrl, executionCallbackToken, RunnerDimensions.builder().from(dimensions).build());
    }

    public RunnerId registerRunner(RunnerDimensions runnerDimensions) {
        return runnerEngineInstance.registerRunner(runnerDimensions.stream().map(Dimension::new).toArray(Dimension[]::new));
    }
}
