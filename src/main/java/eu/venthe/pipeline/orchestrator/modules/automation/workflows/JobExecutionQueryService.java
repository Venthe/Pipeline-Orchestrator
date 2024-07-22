package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.ExecutionDetailsDto;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;

public interface JobExecutionQueryService {
    ExecutionDetailsDto getExecutionDetails(JobExecutionId executionId);
}
