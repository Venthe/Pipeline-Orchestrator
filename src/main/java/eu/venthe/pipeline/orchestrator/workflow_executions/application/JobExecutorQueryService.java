package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.ExecutionId;

public interface JobExecutorQueryService {
    ExecutionDetailsDto getExecutionDetails(ExecutionId executionId);
}
