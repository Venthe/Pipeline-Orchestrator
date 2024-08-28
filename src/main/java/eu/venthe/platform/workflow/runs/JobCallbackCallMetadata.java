package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.workflow.runs.JobRunId;

public record JobCallbackCallMetadata(WorkflowRunId workflowRunId,
                                      JobRunId jobRunId,
                                      RunCallbackToken runCallbackToken) {
}
