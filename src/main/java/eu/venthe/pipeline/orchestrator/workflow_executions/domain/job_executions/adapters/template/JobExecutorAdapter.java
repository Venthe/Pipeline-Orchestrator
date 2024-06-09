package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.Dimension;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.organizations.domain.projects.domain.model.ProjectId;
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
                               ExecutionId executionId,
                               URL systemApiUrl,
                               JobExecutorAdapter.CallbackToken callbackToken,
                               RunnerDimensions dimensions);

        RunnerId registerRunner(Dimension... dimensions);
    }

    record CallbackToken(String value) {
    }
}
