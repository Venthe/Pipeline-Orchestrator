package eu.venthe.pipeline.workflows.model;

import eu.venthe.pipeline.workflows.definition.contexts.JobId;

public record JobRunId(JobId jobId, int value) {
}
