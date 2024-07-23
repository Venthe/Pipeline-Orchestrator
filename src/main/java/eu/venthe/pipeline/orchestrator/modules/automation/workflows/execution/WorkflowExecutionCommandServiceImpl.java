package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.ExecutionAdapterManager;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.api.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
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
    private final ExecutionAdapterManager adapterManager;
    private final EnvUtil envUtil;

    @Override
    public WorkflowExecutionId executeWorkflow(final WorkflowDefinition workflowDefinition, final Context context) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        JobExecutorAdapter.AdapterInstance docker = adapterManager.queryAdapter(new AdapterId("default")).orElseThrow();

        JobExecutionId executionId = new JobExecutionId(UUID.randomUUID().toString());
        docker.queueJobExecution(
                context.id(),
                executionId,
                envUtil.getServerUrl(),
                new JobExecutorAdapter.CallbackToken("TEST_TOKEN"),
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
