package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.api.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.projects.api.components.Workflow;
import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandler;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler implements EventHandler {
    //private final VersionControlSystemProvider versionControlSystem;
    //private final JobExecutor jobExecutor;
    //private final WorkflowExecutionRepository workflowExecutionRepository;
    //private final YAMLMapper yamlMapper;

    @Override
    public Collection<DomainEvent> handle(Project project, Event _event) {
        WorkflowDispatchEvent event = (WorkflowDispatchEvent) _event;

        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        Workflow workflow = project.getWorkflow(event.getRef(), event.getWorkflow())
                .orElseThrow();

        log.trace("Workflow loaded {}", workflow);
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
//
//    private Optional<byte[]> loadWorkflow(WorkflowDispatchHandledEvent workflowDispatchEvent) {
//        return versionControlSystem.getFile(
//                workflowDispatchEvent.getRepository().getName(),
//                workflowDispatchEvent.getRef(),
//                workflowDispatchEvent.getWorkflow()
//        );
//    }
//
//    @SneakyThrows
//    private JsonNode parseYaml(String data) {
//        return yamlMapper.readTree(data);
//    }

    public boolean canHandle(Event event) {
        return event instanceof WorkflowDispatchEvent;
    }

}
