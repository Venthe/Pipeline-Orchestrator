package eu.venthe.platform.workflow.model;

import eu.venthe.platform.workflow.definition.contexts.JobId;

public record JobRunId(JobId jobId, int value) {
}
