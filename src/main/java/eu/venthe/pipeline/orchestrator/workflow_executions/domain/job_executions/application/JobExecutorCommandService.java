package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectId;

public interface JobExecutorCommandService {
    default ExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
