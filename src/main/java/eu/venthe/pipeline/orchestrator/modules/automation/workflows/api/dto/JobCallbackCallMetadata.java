package eu.venthe.pipeline.orchestrator.modules.automation.workflows.api.dto;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      JobExecutionId executionId,
                                      JobExecutorAdapter.CallbackToken callbackToken) {
}
