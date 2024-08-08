package eu.venthe.pipeline.orchestrator.modules.automation.workflows.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.infrastructure.WorkflowRunRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.security.User;
import eu.venthe.pipeline.orchestrator.security.UserService;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.Envelope;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageBroker;
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
public class ProjectWorkflowCommandServiceImpl implements WorkflowExecutionCommandService {
    private final MessageBroker messageBroker;
    private final WorkflowExecutionQueryService workflowExecutionQueryService;
    private final UserService userService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final WorkflowRunRepository repository;
    private final TimeService timeService;
    private final RunnerProvider runnerProvider;

    @SneakyThrows
    @Override
    public WorkflowRunId triggerWorkflowDispatch(final ProjectId id,
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
        messageBroker.exchange(new Envelope<>(event));

        var invoke = new ExponentialBackOff(executorService)
                .invoke(() -> workflowExecutionQueryService.getExecutionDetails(new WorkflowCorrelationId(event.getId().toString())).orElseThrow());

        return invoke.orElseThrow().workflowRunId();
    }

    private static UserContext fromUser(final User user) {
        return UserContext.builder()
                .userType(UserType.USER)
                .login(user.commonName())
                .id(user.commonName())
                .name(user.commonName())
                .build();
    }

    @Override
    public WorkflowRunId triggerWorkflow(final WorkflowDefinition definition, final Context context) {
        var run = new WorkflowRun(definition, context, timeService, null, runnerProvider);
        repository.save(run);
        return run.getId();
    }
}
