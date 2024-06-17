package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.RunnerId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;

@RequiredArgsConstructor
@Value
public class AdapterInstanceAggregate implements Aggregate<AdapterId> {
    AdapterId id;
    JobExecutorAdapter.AdapterInstance adapterInstance;

    public void queueJobExecution(ProjectId projectId, JobExecutionId executionId, URL systemApiUrl, JobExecutorAdapter.CallbackToken callbackToken, Dimension... dimensions) {
        adapterInstance.queueJobExecution(projectId, executionId, systemApiUrl, callbackToken, RunnerDimensions.builder().from(dimensions).build());
    }

    public RunnerId registerRunner(RunnerDimensions runnerDimensions) {
        return adapterInstance.registerRunner(runnerDimensions.stream().map(Dimension::new).toArray(Dimension[]::new));
    }
}
