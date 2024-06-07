package eu.venthe.pipeline.orchestrator.job_executor.domain.model;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;

@RequiredArgsConstructor
@Value
public class AdapterInstanceAggregate implements Aggregate<AdapterId> {
    AdapterId id;
    JobExecutorAdapter.AdapterInstance adapterInstance;

    public void queueJobExecution(ProjectId projectId, ExecutionId executionId, URL systemApiUrl, JobExecutorAdapter.CallbackToken callbackToken, RunnerDimensions.Dimension... dimensions) {
        adapterInstance.queueJobExecution(projectId, executionId, systemApiUrl, callbackToken, new RunnerDimensions(dimensions));
    }

    public RunnerId registerRunner(RunnerDimensions.Dimension... dimensions) {
        return adapterInstance.registerRunner(dimensions);
    }
}
