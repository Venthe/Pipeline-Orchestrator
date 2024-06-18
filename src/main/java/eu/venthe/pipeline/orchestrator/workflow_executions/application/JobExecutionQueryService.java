package eu.venthe.pipeline.orchestrator.workflow_executions.application;

import eu.venthe.pipeline.orchestrator.workflow_executions.application.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.model.JobExecutionId;

public interface JobExecutionQueryService {
    ExecutionDetailsDto getExecutionDetails(JobExecutionId executionId);
}
