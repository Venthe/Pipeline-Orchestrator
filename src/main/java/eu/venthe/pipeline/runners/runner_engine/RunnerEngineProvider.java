package eu.venthe.pipeline.runners.runner_engine;

import eu.venthe.pipeline.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Optional;

public interface RunnerEngineProvider {
    Optional<RunnerEngineInstance> provide(RunnerEngineType runnerEngineType,
                                           SuppliedProperties properties);
}
