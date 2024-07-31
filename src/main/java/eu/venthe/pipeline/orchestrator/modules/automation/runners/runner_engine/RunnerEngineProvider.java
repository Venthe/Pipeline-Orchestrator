package eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;

import java.util.Optional;

public interface RunnerEngineProvider {
    Optional<RunnerEngineInstance> provide(RunnerEngineType runnerEngineType,
                                           SuppliedProperties properties);
}
