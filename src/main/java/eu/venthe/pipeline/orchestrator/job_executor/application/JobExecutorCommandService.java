package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.ContainerId;

import java.util.Map;

public interface JobExecutorCommandService {
    void triggerJobExecution(AdapterId adapterId,
                             ContainerId containerId,
                             Map.Entry<String, String>... dimensions);
}
