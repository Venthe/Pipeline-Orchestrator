package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters;

import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.docker.DockerJobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobExecutorAdapterProvider {
    private final FeatureManager featureManager;

    public JobExecutorAdapter.AdapterInstance provide(AdapterType adapterType, SuppliedProperties properties) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        var dockerJobExecutorAdapter = new DockerJobExecutorAdapter();

        if (!adapterType.equals(dockerJobExecutorAdapter.getAdapterType())) {
            log.error("Adapter of type {} not supported", adapterType);
            throw new UnsupportedOperationException();
        }

        if (featureManager.isActive(new NamedFeature("VALIDATE_JOB_EXECUTOR_ADAPTER_PROPERTIES"))) {
            dockerJobExecutorAdapter.validateProperties(properties);
        }

        log.info("Instantiating job executor adapter {}", adapterType);

        return dockerJobExecutorAdapter.instantiate(properties);
    }
}
