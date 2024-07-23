package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.JobExecutorAdapterProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.model.AdapterInstanceAggregate;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.vo.RegisterAdapterSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.infrastructure.JobExecutorAdapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Component
@RequiredArgsConstructor
public class JobExecutionAdapterManagerImpl implements ExecutionAdapterManager {
    private final JobExecutorAdapterRepository repository;
    private final JobExecutorAdapterProvider jobExecutorAdapterProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public AdapterId registerAdapter(RegisterAdapterSpecification specification) {
        JobExecutorAdapter.AdapterInstance adapterInstance = jobExecutorAdapterProvider.provide(specification.adapterType(), specification.properties());

        repository.save(new AdapterInstanceAggregate(specification.adapterId(), adapterInstance));

        return specification.adapterId();
    }

    @Override
    public RunnerId registerRunner(AdapterId adapterId,
                                   RunnerDimensions dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        AdapterInstanceAggregate docker = repository.find(adapterId).orElseThrow();
        return docker.registerRunner(dimensions);
    }
}
