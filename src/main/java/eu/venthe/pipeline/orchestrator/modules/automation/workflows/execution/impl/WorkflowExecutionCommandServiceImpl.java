package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerManager;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.RunnerEngineInstance;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.dimensions.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class WorkflowExecutionCommandServiceImpl implements WorkflowExecutionCommandService {
    private final FeatureManager featureManager;
    private final RunnerManager adapterManager;
    private final EnvUtil envUtil;

    @Override
    public WorkflowExecutionId triggerWorkflow(final WorkflowDefinition workflowDefinition, final Context context) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        RunnerEngineInstance docker = adapterManager.queryAdapter(new RunnerImplementationId("default")).orElseThrow();

        JobExecutionId executionId = new JobExecutionId(UUID.randomUUID().toString());
        docker.queueExecution(
                context.id(),
                executionId,
                envUtil.getServerUrl(),
                new ExecutionCallbackToken("TEST_TOKEN"),
                RunnerDimensions.builder().from(context.dimensions().toArray(Dimension[]::new)).build()
        );
        return executionId;
    }



/*
    @SneakyThrows
    @Override
    public JobExecutionId triggerJobExecution(ProjectId projectId, Dimension... dimensions) {

    }*/
}
