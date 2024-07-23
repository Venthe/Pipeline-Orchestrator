package eu.venthe.pipeline.orchestrator.modules.automation.workflows.api;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.api.dto.JobCallbackCallMetadata;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.api.dto.JobCallbackLogEntry;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.api.dto.JobExecutionContext;
import lombok.RequiredArgsConstructor;

import java.io.File;

public interface JobExecutorCallbackService {
    default JobExecutionContext requestContext(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionProgressed(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionStarted(JobCallbackCallMetadata callMetadata) {
        throw new UnsupportedOperationException();
    }

    default void jobExecutionCompleted(JobCallbackCallMetadata callMetadata) {
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
