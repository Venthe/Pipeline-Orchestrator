package eu.venthe.platform.workflow.impl;

import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;
import eu.venthe.platform.workflow.runs.WorkflowCorrelationId;
import eu.venthe.platform.workflow.runs.WorkflowRun;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import eu.venthe.platform.workflow.runs.dependencies.TriggeringEntity;
import eu.venthe.platform.workflow.runs.infrastructure.WorkflowRunRepository;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.application.security.User;
import eu.venthe.platform.application.security.UserService;
import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.shared_kernel.system_events.EventId;
import eu.venthe.platform.shared_kernel.system_events.ProjectEvent;
import eu.venthe.platform.shared_kernel.system_events.WorkflowDispatchEvent;
import eu.venthe.platform.shared_kernel.system_events.contexts.RepositoryContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.UserContext;
import eu.venthe.platform.shared_kernel.system_events.model.UserType;
import eu.venthe.platform.application.utilities.ExponentialBackOff;
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

import static eu.venthe.platform.workflow.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

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
                                                 final SimpleRevision simpleRevision,
                                                 final Path workflowPath) {
        var eventId = UUID.randomUUID();
        ProjectEvent event = WorkflowDispatchEvent.builder()
                .workflow(resolveFromOrchestratorDirectory(workflowPath))
                .revision(simpleRevision)
                .id(new EventId(eventId))
                .repository(RepositoryContext.builder().id(id).build())
                .sender(fromUser(userService.getCurrentUser()))
                .build();
        messageBroker.exchange(new Envelope<>(event));

        var invoke = new ExponentialBackOff()
                .invoke(() -> workflowRunQueryService.getExecutionDetails(new WorkflowCorrelationId(event.getId().serialize())).orElseThrow());

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
    public WorkflowRunId triggerWorkflow(WorkflowDefinition definition, Context context, TriggeringEntity triggeringEntity) {
        var pair = WorkflowRun.crate(definition, context, timeService, triggeringEntity);
        var workflowRun = pair.getRight();
        repository.save(workflowRun);
        var runEvents = workflowRun.run();
        var creationEvents = pair.getLeft();
        Collection<Envelope<?>> allEvents = Stream.concat(runEvents.stream(), creationEvents.stream())
                .map(Envelope::new)
                .collect(Collectors.toUnmodifiableList());
        messageBroker.exchange(allEvents);
        return workflowRun.getId();
    }
}
