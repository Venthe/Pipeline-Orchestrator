/*
package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.infrastructure.WorkflowRunRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

@RequiredArgsConstructor
@Service
public class WorkflowExecutionCommandServiceImpl implements WorkflowExecutionCommandService {
    private final FeatureManager featureManager;
    private final WorkflowRunRepository repository;
    private final EnvUtil envUtil;

    @Override
    public WorkflowRunId triggerWorkflow(final WorkflowDefinition workflowDefinition,
                                         final Context context) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        final var execution = new WorkflowExecution(null, workflowDefinition);

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
*/
