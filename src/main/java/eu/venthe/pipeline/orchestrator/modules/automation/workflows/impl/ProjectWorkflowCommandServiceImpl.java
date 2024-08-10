package eu.venthe.pipeline.orchestrator.modules.automation.workflows.impl;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowRunCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowRunQueryService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowCorrelationId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.infrastructure.WorkflowRunRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.security.User;
import eu.venthe.pipeline.orchestrator.security.UserService;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.Envelope;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.GitRevision;
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
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
@Service
public class ProjectWorkflowCommandServiceImpl implements WorkflowRunCommandService {
    private final MessageBroker messageBroker;
    private final WorkflowRunQueryService workflowRunQueryService;
    private final UserService userService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final WorkflowRunRepository repository;
    private final TimeService timeService;

    @SneakyThrows
    @Override
    public WorkflowRunId triggerWorkflowDispatch(final ProjectId id,
                                                 final GitRevision revision,
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
                .invoke(() -> workflowRunQueryService.getExecutionDetails(new WorkflowCorrelationId(event.getId().toString())).orElseThrow());

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
        var pair = WorkflowRun.crate(definition, context, timeService, null);
        var workflowRun = pair.getRight();
        repository.save(new WorkflowRunRepository.Aggregate(new WorkflowRunRepository.Id(context.id(), workflowRun.getId()), workflowRun));
        var runEvents = workflowRun.run();
        var creationEvents = pair.getLeft();
        Collection<Envelope<?>> allEvents = Stream.concat(runEvents.stream(), creationEvents.stream())
                .map(Envelope::new)
                .collect(Collectors.toUnmodifiableList());
        messageBroker.exchange(allEvents);
        return workflowRun.getId();
    }
}
