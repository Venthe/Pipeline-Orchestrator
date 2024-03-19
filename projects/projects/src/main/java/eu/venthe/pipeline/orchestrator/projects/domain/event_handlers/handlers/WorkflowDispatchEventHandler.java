package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.api.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.events.WorkflowDispatchEventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler extends AbstractEventHandler<WorkflowDispatchEvent> {
    @Override
    public Collection<DomainEvent> _handle(Project project, WorkflowDispatchEvent event) {
        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        var workflow = project.getWorkflow(event.getRef(), event.getWorkflow())
                .orElseThrow();

        log.trace("Workflow loaded {}", workflow);

        if (!workflow.on(new WorkflowDispatchEventWrapper(event))) {
            return Collections.emptyList();
        }
//
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

    public boolean canHandle(Event event) {
        return event instanceof WorkflowDispatchEvent;
    }
}
