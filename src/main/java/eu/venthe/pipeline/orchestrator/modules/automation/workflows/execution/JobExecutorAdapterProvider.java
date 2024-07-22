package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterType;
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
public class JobExecutorAdapterProvider {
    private final FeatureManager featureManager;
    private final AdapterProviders adapters;

    public JobExecutorAdapter.AdapterInstance provide(AdapterType adapterType, SuppliedProperties properties) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        var executorAdapter = adapters.provide(adapterType).orElseThrow();

        if (!adapterType.equals(executorAdapter.getAdapterType())) {
            log.error("Adapter of type {} not supported", adapterType);
            throw new UnsupportedOperationException();
        }

        if (featureManager.isActive(new NamedFeature("VALIDATE_JOB_EXECUTOR_ADAPTER_PROPERTIES"))) {
            executorAdapter.validateProperties(properties);
        }

        log.trace("Instantiating job executor adapter {}", adapterType);

        var instantiate = executorAdapter.instantiate(properties);

        log.info("Job executor for adapter {} instantiated", adapterType);
        return instantiate;
    }

    @ToString
    @EqualsAndHashCode
    @Component
    public static class AdapterProviders {
        private final Map<AdapterType, JobExecutorAdapter> adapters;

        public AdapterProviders(final Set<JobExecutorAdapter> adapters) {
            this.adapters = adapters.stream()
                    .collect(Collectors.toMap(
                            JobExecutorAdapter::getAdapterType,
                            Function.identity()
                    ));
        }

        public Optional<JobExecutorAdapter> provide(AdapterType adapterType) {
            return Optional.ofNullable(adapters.get(adapterType));
        }
    }
}
