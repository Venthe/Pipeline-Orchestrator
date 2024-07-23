package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution;

import eu.venthe.pipeline.orchestrator.modules.ProjectModuleMediator;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;
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
public class WorkflowExecutionCommandServiceImpl implements WorkflowExecutionCommandService {
    private final ProjectModuleMediator moduleMediator;
    private final WorkflowExecutionQueryService workflowExecutionQueryService;
    private final UserService userService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @SneakyThrows
    @Override
    public WorkflowExecutionId triggerManualWorkflow(final ProjectId projectId,
                                                     final Revision revision,
                                                     final Path path) {
        var eventId = UUID.randomUUID();
        ProjectEvent event = WorkflowDispatchEvent.builder()
                .workflow(resolveFromOrchestratorDirectory(path))
                .revision(revision)
                .id(new EventId(eventId))
                .repository(RepositoryContext.builder().id(projectId).build())
                .sender(fromUser(userService.getCurrentUser()))
                .build();
        moduleMediator.listen(event);

        var invoke = new ExponentialBackOff(executorService).invoke(() -> workflowExecutionQueryService.getExecutionDetails(new WorkflowCorrelationId(event.getId().toString())).orElseThrow());

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
