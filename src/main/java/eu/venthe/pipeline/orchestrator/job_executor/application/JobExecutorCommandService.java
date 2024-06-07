package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;

public interface JobExecutorCommandService {
    default ExecutionId triggerJobExecution(ProjectId projectId, RunnerDimensions.Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
