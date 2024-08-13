package eu.venthe.pipeline.workflow.runs;

import eu.venthe.pipeline.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.project.domain.ProjectId;

public record JobCallbackCallMetadata(ProjectId projectId,
                                      WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
