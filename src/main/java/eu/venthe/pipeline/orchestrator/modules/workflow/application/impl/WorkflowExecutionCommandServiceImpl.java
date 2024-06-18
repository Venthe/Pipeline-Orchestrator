package eu.venthe.pipeline.orchestrator.modules.workflow.application.impl;

import eu.venthe.pipeline.orchestrator.modules.workflow.application.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.WorkflowExecutionQueryService;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsCommandService;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static eu.venthe.pipeline.orchestrator.modules.workflow.domain.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
@Service
public class WorkflowExecutionCommandServiceImpl implements WorkflowExecutionCommandService {
    private final ProjectsCommandService projectsCommandService;
    private final ProjectsQueryService projectsQueryService;
    private final WorkflowExecutionQueryService workflowExecutionQueryService;
    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @SneakyThrows
    @Override
    public WorkflowExecutionId triggerManualWorkflow(final ProjectId projectId, final Revision revision, final Path path) {
        var details = projectsQueryService.getProjectDetails(projectId);

        var eventId = UUID.randomUUID();
        ProjectEvent event = WorkflowDispatchEvent.builder()
                .workflow(resolveFromOrchestratorDirectory(path))
                .revision(revision)
                .id(eventId)
                .repository(null)
                .sender(null)
                .build();
        projectsCommandService.handleEvent(projectId, event);

        workflowExecutionQueryService.getExecutionDetails(new EventId(event.toString()));

        throw new UnsupportedOperationException();
    }
}
