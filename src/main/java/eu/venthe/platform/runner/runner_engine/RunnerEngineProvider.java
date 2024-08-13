package eu.venthe.platform.runner.runner_engine;

import eu.venthe.platform.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerEngineType;
import eu.venthe.platform.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Optional;

public interface RunnerEngineProvider {
    Optional<RunnerEngineInstance> provide(RunnerEngineType runnerEngineType,
                                           SuppliedProperties properties);
}
