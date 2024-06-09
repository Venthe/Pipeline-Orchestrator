package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.transport.DockerHttpClient;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.adapters.template.model.AdapterType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DockerJobExecutorAdapter implements JobExecutorAdapter {

    @Override
    public AdapterType getAdapterType() {
        return new AdapterType("docker");
    }

    @Override
    public AdapterInstance instantiate(SuppliedProperties properties) {
        DockerClientConfig dockerClientConfig = DockerConfig.dockerClientConfig();
        DockerHttpClient dockerHttpClient = DockerConfig.dockerHttpClient(dockerClientConfig);
        DockerClient dockerClient = DockerConfig.dockerClient(dockerClientConfig, dockerHttpClient);

        return new DockerJobExecutorAdapterInstance(dockerClient);
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return Map.of();
    }
}
