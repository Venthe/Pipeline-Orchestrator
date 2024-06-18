package eu.venthe.pipeline.orchestrator.modules.workflow.application;

import eu.venthe.pipeline.orchestrator.modules.workflow.application.dto.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;

public interface JobExecutionQueryService {
    ExecutionDetailsDto getExecutionDetails(JobExecutionId executionId);
}
