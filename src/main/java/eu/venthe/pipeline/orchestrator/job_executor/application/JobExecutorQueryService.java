package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;

public interface JobExecutorQueryService {
    ExecutionDetailsDto getExecutionDetails(ExecutionId executionId);
}
