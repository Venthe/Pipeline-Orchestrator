package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.query;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      JobExecutionId executionId,
                                      ExecutionCallbackToken executionCallbackToken) {
}
