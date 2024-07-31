package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Map;

/**
 * Provides a template for the particular running engine
 */
public interface RunnerEngineDefinition {
    /**
     * Provides discriminator for the runner engine definition.
     */
    RunnerEngineType getEngineType();

    /**
     * Creates configured instance of the engine.
     */
    // TODO: Add abstract validation for the RunnerEngineDefinition
    RunnerEngineInstance instantiate(SuppliedProperties properties);

    /**
     * Informs about properties (and their types) required for instantiation.
     */
    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();
}
