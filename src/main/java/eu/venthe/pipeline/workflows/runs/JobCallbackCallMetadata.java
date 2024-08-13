package eu.venthe.pipeline.workflows.runs;

import eu.venthe.pipeline.runners.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.workflows.model.JobRunId;
import eu.venthe.pipeline.projects.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
