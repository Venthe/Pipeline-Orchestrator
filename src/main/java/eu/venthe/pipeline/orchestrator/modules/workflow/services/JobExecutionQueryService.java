package eu.venthe.pipeline.orchestrator.modules.workflow.services;

import eu.venthe.pipeline.orchestrator.modules.workflow.services.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;

public interface JobExecutionQueryService {
    ExecutionDetailsDto getExecutionDetails(JobExecutionId executionId);
}
