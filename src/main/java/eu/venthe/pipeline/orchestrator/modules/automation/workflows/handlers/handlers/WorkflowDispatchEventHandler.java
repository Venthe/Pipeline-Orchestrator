package eu.venthe.pipeline.orchestrator.modules.automation.workflows.handlers.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowRunCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.events.WorkflowDispatchEventWrapper;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler extends AbstractEventHandler<WorkflowDispatchEvent> {
    private final ObjectMapper mapper;
    private final ProjectsQueryService projectsQueryService;
    private final WorkflowRunCommandService workflowRunCommandService;

    @Override
    public Collection<DomainTrigger> _handle(WorkflowDispatchEvent event) {
        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        var workflowDefinition = projectsQueryService.getFile(event.getRepository().getId(), event.getRevision(), event.getWorkflow())
                .map(e -> e.content().toString())
                .map(this::getTree)
                .map(WorkflowDefinition::new)
                .orElseThrow();

        log.trace("Workflow loaded {}", workflowDefinition);

        if (!workflowDefinition.on(new WorkflowDispatchEventWrapper(event))) {
            return Collections.emptyList();
        }

        workflowRunCommandService.triggerWorkflow(workflowDefinition, new WorkflowRunCommandService.Context(event.getRepository().getId(), event.getRevision(), new HashSet<>()));

        return Collections.emptyList();
    }

    @SneakyThrows
    private JsonNode getTree(String content) {
        return mapper.readTree(content);
    }

    @Override
    public boolean canHandle(ProjectEvent event) {
        return event instanceof WorkflowDispatchEvent;
    }
}
