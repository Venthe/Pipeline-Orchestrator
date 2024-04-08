package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.WorkflowFactory;
import eu.venthe.pipeline.orchestrator.projects.domain.events.WorkflowDispatchEventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.WorkflowDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler extends AbstractEventHandler<WorkflowDispatchEvent> {
    private final VersionControlSystemProvider versionControlSystemProvider;
    private final WorkflowFactory workflowFactory;

    @Override
    public Collection<DomainEvent> _handle(Project project, WorkflowDispatchEvent event) {
        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        var workflow = project.getWorkflow(event.getRef(), event.getWorkflow().toString()).apply(versionControlSystemProvider, workflowFactory)
                .orElseThrow();

        log.trace("Workflow loaded {}", workflow);

        if (!workflow.on(new WorkflowDispatchEventWrapper(event))) {
            return Collections.emptyList();
        }

//        Optional<WorkflowExecution> workflowExecution = WorkflowExecution.from(workflow, workflowDispatchEvent);
//        workflowExecution.ifPresentOrElse(we -> {
//            we.start(jobExecutor);
//
//            workflowExecutionRepository.save(we);
//        }, () -> log.debug("No workflow execution is created"));
//
//        return workflowExecution.map(WorkflowExecution::getId);

        throw new UnsupportedOperationException();
    }

    public boolean canHandle(ProjectEvent event) {
        return event instanceof WorkflowDispatchEvent;
    }
}
