package eu.venthe.pipeline.orchestrator.modules.automation.workflows.model;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;

public record JobRunId(JobId jobId, int value) {
}
