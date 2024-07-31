package eu.venthe.pipeline.orchestrator.modules.automation.workflows.impl;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.ProjectWorkflowCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.workflow_execution.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.security.User;
import eu.venthe.pipeline.orchestrator.security.UserService;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.EventId;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.UserType;
import eu.venthe.pipeline.orchestrator.utilities.ExponentialBackOff;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
@Service
public class ProjectWorkflowCommandServiceImpl implements ProjectWorkflowCommandService {
    private final ProjectModuleMediator moduleMediator;
    private final WorkflowExecutionQueryService workflowExecutionQueryService;
    private final UserService userService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @SneakyThrows
    @Override
    public WorkflowExecutionId triggerWorkflowDispatch(final ProjectId id,
                                                       final Revision revision,
                                                       final Path workflowPath) {
        var eventId = UUID.randomUUID();
        ProjectEvent event = WorkflowDispatchEvent.builder()
                .workflow(resolveFromOrchestratorDirectory(workflowPath))
                .revision(revision)
                .id(new EventId(eventId))
                .repository(RepositoryContext.builder().id(id).build())
                .sender(fromUser(userService.getCurrentUser()))
                .build();
        moduleMediator.listen(event);

        var invoke = new ExponentialBackOff(executorService)
                .invoke(() -> workflowExecutionQueryService.getExecutionDetails(new WorkflowCorrelationId(event.getId().toString())).orElseThrow());

        return invoke.orElseThrow().workflowExecutionId();
    }

    private static UserContext fromUser(final User user) {
        return UserContext.builder()
                .userType(UserType.USER)
                .login(user.commonName())
                .id(user.commonName())
                .name(user.commonName())
                .build();
    }
}
