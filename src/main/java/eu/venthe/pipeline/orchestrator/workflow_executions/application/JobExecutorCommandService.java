package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.model.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.ProjectId;

public interface JobExecutorCommandService {
    default JobExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {
        throw new UnsupportedOperationException();
    }
}
