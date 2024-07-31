package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.transport.DockerHttpClient;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.ConfigurationPropertyDefinition;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.PropertyName;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DockerEngineDefinition implements RunnerEngineDefinition {

    @Override
    public RunnerEngineType getEngineType() {
        return new RunnerEngineType("docker");
    }

    @Override
    public RunnerEngineInstance instantiate(SuppliedProperties properties) {
        DockerClientConfig dockerClientConfig = DockerConfig.dockerClientConfig();
        DockerHttpClient dockerHttpClient = DockerConfig.dockerHttpClient(dockerClientConfig);
        DockerClient dockerClient = DockerConfig.dockerClient(dockerClientConfig, dockerHttpClient);

        return new DockerEngineInstance(dockerClient);
    }

    @Override
    public Map<PropertyName, ConfigurationPropertyDefinition> getConfigurationPropertiesDefinitions() {
        return Map.of();
    }
}
