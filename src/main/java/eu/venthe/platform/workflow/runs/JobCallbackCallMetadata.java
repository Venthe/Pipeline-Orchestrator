package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.workflow.model.JobRunId;

public record JobCallbackCallMetadata(WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
