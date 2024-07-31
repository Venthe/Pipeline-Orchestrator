package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.infrastructure.WorkflowExecutionRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowExecution;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@RequiredArgsConstructor
@Service
public class WorkflowExecutionCommandServiceImpl implements WorkflowExecutionCommandService {
    private final FeatureManager featureManager;
    private final WorkflowExecutionRepository repository;
    private final EnvUtil envUtil;

    @Override
    public WorkflowExecutionId triggerWorkflow(final WorkflowDefinition workflowDefinition,
                                               final Context context) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        final var execution = new WorkflowExecution(workflowDefinition);

        // TODO: Make it asynchronous
        execution.triggerWorkflow(context, envUtil);

        repository.save(execution);

        return execution.getId();

        // TODO: Create a WorkflowExecution
        //JobExecutionId executionId = new JobExecutionId(UUID.randomUUID().toString());
        //adapterManager.queueExecution(
        //        context.id(),
        //        executionId,
        //        envUtil.getServerUrl(),
        //        new ExecutionCallbackToken("TEST_TOKEN"),
        //        RunnerDimensions.builder().from(context.dimensions().toArray(Dimension[]::new)).build()
        //);
    }
}
