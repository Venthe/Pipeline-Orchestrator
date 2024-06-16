package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.ExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;

public interface JobExecutorCommandService {
    default ExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
