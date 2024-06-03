package eu.venthe.pipeline.orchestrator.job_executor.adapters;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.docker.DockerJobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.gerrit.GerritProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects.source_configuration.plugins.template.model.SourceType;
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
