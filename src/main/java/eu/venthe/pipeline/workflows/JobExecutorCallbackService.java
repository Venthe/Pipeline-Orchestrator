package eu.venthe.pipeline.workflows;

import eu.venthe.pipeline.workflows.runs.JobCallbackCallMetadata;
import eu.venthe.pipeline.workflows.runs.jobs.steps.JobCallbackLogEntry;

import java.io.File;

public interface JobExecutorCallbackService {
    default void jobRunProgressed(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobRunStarted(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobRunCompleted(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadLog(JobCallbackLogEntry log, JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default File downloadArtifact(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadArtifact(File file, JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void uploadCache(File file, JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default File downloadCache(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

}
