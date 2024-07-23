package eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.net.URL;
import java.util.Map;

public interface JobExecutorAdapter {
    default void validateProperties(SuppliedProperties properties) {
        throw new UnsupportedOperationException();
    }

    AdapterType getAdapterType();

    AdapterInstance instantiate(SuppliedProperties properties);

    Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions();

    interface AdapterInstance {
        void queueJobExecution(ProjectId projectId,
                               JobExecutionId executionId,
                               URL systemApiUrl,
                               JobExecutorAdapter.CallbackToken callbackToken,
                               RunnerDimensions dimensions);

        RunnerId registerRunner(Dimension... dimensions);
    }

    record CallbackToken(String value) {
    }

    record AdapterId(String value) {
    }
}
