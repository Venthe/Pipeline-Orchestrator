package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.shared_kernel.project.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
