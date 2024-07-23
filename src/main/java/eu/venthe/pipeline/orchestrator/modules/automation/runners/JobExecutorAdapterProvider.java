package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

public interface JobExecutorAdapterProvider {
    JobExecutorAdapter.AdapterInstance provide(AdapterType adapterType, SuppliedProperties properties);
}
