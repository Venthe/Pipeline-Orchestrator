package eu.venthe.pipeline.runner.impl;

import eu.venthe.pipeline.runner.RunnerManager;
import eu.venthe.pipeline.runner.infrastructure.RunnerEngineInstanceRepository;
import eu.venthe.pipeline.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.runner.model.RunnerEngineInstanceAggregate;
import eu.venthe.pipeline.runner.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.runner.runner_engine.RunnerEngineProvider;
import eu.venthe.pipeline.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.runner.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.workflow.model.JobRunId;
import eu.venthe.pipeline.workflow.runs.WorkflowRunId;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.application.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@Component
@RequiredArgsConstructor
public class RunnerManagerImpl implements RunnerManager {
    private final RunnerEngineInstanceRepository repository;
    private final RunnerEngineProvider runnerEngineProvider;
    private final FeatureManager featureManager;
    private final EnvUtil envUtil;

    @Override
    public RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification) {
        RunnerEngineInstance runnerEngineInstance = runnerEngineProvider.provide(specification.runnerEngineType(), specification.properties()).orElseThrow();

        repository.save(new RunnerEngineInstanceAggregate(specification.runnerEngineInstanceId(), runnerEngineInstance));

        return specification.runnerEngineInstanceId();
    }

    @Override
    public RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                                   RunnerDimensions dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        RunnerEngineInstanceAggregate aggregate = repository.find(runnerEngineInstanceId).orElseThrow();
        return aggregate.registerRunner(dimensions);
    }

    @Override
    public boolean queueExecution(final ProjectId projectId,
                                  final WorkflowRunId workflowRunId,
                                  final JobRunId executionId,
                                  final RunCallbackToken runCallbackToken,
                                  final RunnerDimensions dimensions) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        // TODO: Find aggregate by runner dimensions:
        //  default or org id
        // FIXME: Actually implement
        var aggregate = repository.findAll().stream().findAny().orElseThrow();

        aggregate.queueJobExecution(
                projectId,
                workflowRunId,
                executionId,
                envUtil.getServerUrl(),
                runCallbackToken,
                dimensions
        );

        return true;
    }
}
