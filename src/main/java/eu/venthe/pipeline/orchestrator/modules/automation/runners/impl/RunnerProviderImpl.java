package eu.venthe.pipeline.orchestrator.modules.automation.runners.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.orchestrator.shared_kernel.configuration_properties.SuppliedProperties;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RunnerProviderImpl implements RunnerProvider {
    private final FeatureManager featureManager;
    private final AdapterProviders adapters;

    @Override
    public RunnerEngineInstance provide(RunnerEngineType runnerEngineType, SuppliedProperties properties) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        var executorAdapter = adapters.provide(runnerEngineType).orElseThrow();

        if (!runnerEngineType.equals(executorAdapter.getEngineType())) {
            log.error("Adapter of type {} not supported", runnerEngineType);
            throw new UnsupportedOperationException();
        }

        log.trace("Instantiating job executor adapter {}", runnerEngineType);

        var instantiate = executorAdapter.instantiate(properties);

        log.info("Job executor for adapter {} instantiated", runnerEngineType);
        return instantiate;
    }

    @ToString
    @EqualsAndHashCode
    @Component
    public static class AdapterProviders {
        private final Map<RunnerEngineType, RunnerEngineDefinition> adapters;

        public AdapterProviders(final Set<RunnerEngineDefinition> adapters) {
            this.adapters = adapters.stream()
                    .collect(Collectors.toMap(
                            RunnerEngineDefinition::getEngineType,
                            Function.identity()
                    ));
        }

        public Optional<RunnerEngineDefinition> provide(RunnerEngineType runnerEngineType) {
            return Optional.ofNullable(adapters.get(runnerEngineType));
        }
    }
}
