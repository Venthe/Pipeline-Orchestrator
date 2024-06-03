package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface JobExecutorAdapterManager {

    AdapterId registerGlobal(AdapterId adapterId, AdapterType adapterType, SuppliedProperties properties);

    String registerRunner(AdapterId executorId, String s);
}
