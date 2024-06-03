package eu.venthe.pipeline.orchestrator.job_executor.adapters.template;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Map;

public interface JobExecutorAdapter {
    default void validateProperties(SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }

    AdapterType getAdapterType();

    AdapterInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface AdapterInstance {
        void queueJobExecution();
    }
}
