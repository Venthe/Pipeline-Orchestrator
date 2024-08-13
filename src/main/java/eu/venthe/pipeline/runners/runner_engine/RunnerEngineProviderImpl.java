package eu.venthe.pipeline.runners.runner_engine;

import eu.venthe.pipeline.runners.runner_engine.template.RunnerEngineDefinition;
import eu.venthe.pipeline.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.runners.runner_engine.template.model.RunnerEngineType;
import eu.venthe.pipeline.shared_kernel.configuration_properties.SuppliedProperties;
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
public class RunnerEngineProviderImpl implements RunnerEngineProvider {
    private final FeatureManager featureManager;
    private final EngineDefinitions engineDefinitions;

    @Override
    public Optional<RunnerEngineInstance> provide(RunnerEngineType runnerEngineType, SuppliedProperties properties) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        var engineDefinition = engineDefinitions.provide(runnerEngineType).orElseThrow();

        if (!runnerEngineType.equals(engineDefinition.getEngineType())) {
            log.error("Adapter of type {} not supported", runnerEngineType);
            throw new UnsupportedOperationException();
        }

        log.trace("Instantiating job executor adapter {}", runnerEngineType);

        var instance = engineDefinition.instantiate(properties);

        log.info("Job executor for adapter {} instantiated", runnerEngineType);
        return Optional.of(instance);
    }

    @ToString
    @EqualsAndHashCode
    @Component
    public static class EngineDefinitions {
        private final Map<RunnerEngineType, RunnerEngineDefinition> definitions;

        public EngineDefinitions(final Set<RunnerEngineDefinition> definitions) {
            this.definitions = definitions.stream()
                    .collect(Collectors.toMap(
                            RunnerEngineDefinition::getEngineType,
                            Function.identity()
                    ));
        }

        public Optional<RunnerEngineDefinition> provide(RunnerEngineType runnerEngineType) {
            return Optional.ofNullable(definitions.get(runnerEngineType));
        }
    }
}
