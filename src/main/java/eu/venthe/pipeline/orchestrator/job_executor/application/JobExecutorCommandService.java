package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;

import java.util.Map;

public interface JobExecutorCommandService {
    default void triggerJobExecution(AdapterId adapterId,
                             Map.Entry<String, String>... dimensions) {
        throw new UnsupportedOperationException();
    }
}
