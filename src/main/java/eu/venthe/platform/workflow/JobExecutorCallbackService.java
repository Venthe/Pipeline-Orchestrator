package eu.venthe.platform.workflow;

import eu.venthe.platform.workflow.definition._archive.steps.StepId;
import eu.venthe.platform.workflow.runs.JobCallbackCallMetadata;
import eu.venthe.platform.workflow.runs.JobExecutorCallbackServiceImpl;
import eu.venthe.platform.workflow.runs.jobs.steps.JobCallbackLogEntry;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.Map;

public interface JobExecutorCallbackService {
    default void jobRunProgressed(JobCallbackCallMetadata callMetadata, Map<StepId, JobExecutorCallbackServiceImpl.Progress> progress) {
        throw new UnsupportedOperationException();
    }

    default void jobRunStarted(JobCallbackCallMetadata callMetadata, ZonedDateTime startedAt) {
        throw new UnsupportedOperationException();
    }

    default void jobRunCompleted(JobCallbackCallMetadata callMetadata, ZonedDateTime completedAt, Map<String, String> outputs) {
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
