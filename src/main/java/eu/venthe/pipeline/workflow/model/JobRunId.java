package eu.venthe.pipeline.workflow.model;

import eu.venthe.pipeline.workflow.definition.contexts.JobId;

public record JobRunId(JobId jobId, int value) {
}
