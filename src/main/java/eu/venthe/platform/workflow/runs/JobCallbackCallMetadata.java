package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.project.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
