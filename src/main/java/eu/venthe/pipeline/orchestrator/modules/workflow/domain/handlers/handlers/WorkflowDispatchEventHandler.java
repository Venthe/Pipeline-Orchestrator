package eu.venthe.pipeline.orchestrator.modules.workflow.domain.handlers.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.events.WorkflowDispatchEventWrapper;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.workflows.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSpecifiedDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler extends AbstractEventHandler<WorkflowDispatchEvent> {
    private final ObjectMapper mapper;

    @Override
    public Collection<DomainTrigger> _handle(ProjectSpecifiedDataProvider provider, WorkflowDispatchEvent event) {
        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        var workflow = provider.getFile(event.getRevision().value(), event.getWorkflow())
                .map(e -> new String(e, StandardCharsets.UTF_8))
                .map(this::getTree)
                // FIXME
                .map(e -> new WorkflowDefinition(e, null))
                .orElseThrow();

        log.trace("Workflow loaded {}", workflow);

        if (!workflow.on(new WorkflowDispatchEventWrapper(event))) {
            return Collections.emptyList();
        }

        //  Optional<WorkflowExecution> workflowExecution = WorkflowExecution.from(workflow, workflowDispatchEvent);
        //  workflowExecution.ifPresentOrElse(we -> {
        //      we.start(jobExecutor);
        //
        //      workflowExecutionRepository.save(we);
        //  }, () -> log.debug("No workflow execution is created"));
        //
        //  return workflowExecution.map(WorkflowExecution::getId);

        throw new UnsupportedOperationException();
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
