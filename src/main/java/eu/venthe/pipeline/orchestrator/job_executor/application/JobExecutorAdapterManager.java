package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Map;

public interface JobExecutorAdapterManager {

    AdapterId registerExecutorAdapter(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties);

    RunnerId registerRunner(AdapterId adapterId,
                            Map.Entry<String, String>... dimensions);
}
