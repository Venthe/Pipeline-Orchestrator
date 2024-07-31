package eu.venthe.pipeline.orchestrator.modules.automation.runners.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerManager;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RegisterRunnerImplementationSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RunnerId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure.JobExecutorAdapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Component
@RequiredArgsConstructor
public class RunnerManagerImpl implements RunnerManager {
    private final JobExecutorAdapterRepository repository;
    private final RunnerProvider runnerProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public RunnerImplementationId registerRunnerImplementation(RegisterRunnerImplementationSpecification specification) {
        RunnerEngineInstance runnerEngineInstance = runnerProvider.provide(specification.runnerEngineType(), specification.properties());

        repository.save(new AdapterInstanceAggregate(specification.runnerImplementationId(), runnerEngineInstance));

        return specification.runnerImplementationId();
    }

    @Override
    public RunnerId registerRunner(RunnerImplementationId runnerImplementationId,
                                   RunnerDimensions dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(runnerImplementationId).orElseThrow();
        return docker.registerRunner(dimensions);
    }
}
