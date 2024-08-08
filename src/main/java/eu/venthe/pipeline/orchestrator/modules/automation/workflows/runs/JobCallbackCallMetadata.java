package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      ExecutionCallbackToken executionCallbackToken) {
}
