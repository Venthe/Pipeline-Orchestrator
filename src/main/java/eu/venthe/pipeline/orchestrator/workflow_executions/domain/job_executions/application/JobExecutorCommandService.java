package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.projects.domain.model.ProjectId;

public interface JobExecutorCommandService {
    default ExecutionId triggerJobExecution(ProjectId projectId, RunnerDimensions.Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
