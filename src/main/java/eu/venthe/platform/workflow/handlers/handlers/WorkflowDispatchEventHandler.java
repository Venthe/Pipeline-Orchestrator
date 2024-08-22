package eu.venthe.platform.workflow.handlers.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.system_events.RepositoryEvent;
import eu.venthe.platform.shared_kernel.system_events.WorkflowDispatchEvent;
import eu.venthe.platform.workflow.WorkflowRunCommandService;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;
import eu.venthe.platform.workflow.events.WorkflowDispatchEventWrapper;
import eu.venthe.platform.workflow.runs.WorkflowCorrelationId;
import eu.venthe.platform.workflow.runs.dependencies.Actor;
import eu.venthe.platform.workflow.runs.dependencies.TriggeringEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler extends AbstractEventHandler<WorkflowDispatchEvent> {
    private final ObjectMapper mapper;
    private final RepositoryQueryService repositorysQueryService;
    private final WorkflowRunCommandService workflowRunCommandService;

    @Override
    public Collection<DomainTrigger> _handle(WorkflowDispatchEvent event) {
        log.info("Event triggers single workflow on path {}", event.getWorkflow());

        var workflowDefinition = repositorysQueryService.getFile(event.getRepository().getId(), event.getRevision(), event.getWorkflow())
                .map(e -> {
                    try (var content = e.content()) {
                        return new String(content.readAllBytes(), StandardCharsets.UTF_8);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .map(this::getTree)
                .map(WorkflowDefinition::new)
                .orElseThrow();

        log.trace("Workflow loaded {}", workflowDefinition);

        if (!workflowDefinition.on(new WorkflowDispatchEventWrapper(event))) {
            return Collections.emptyList();
        }

        workflowRunCommandService.triggerWorkflow(workflowDefinition, new WorkflowRunCommandService.Context(event.getRepository().getId(), event.getRevision(), new HashSet<>()), new TriggeringEntity() {
            @Override
            public Actor getActor() {
                return null;
            }

            @Override
            public WorkflowCorrelationId getCorrelationId() {
                return new WorkflowCorrelationId(event.getId().serialize());
            }
        });

        return Collections.emptyList();
    }

    @SneakyThrows
    private JsonNode getTree(String content) {
        return mapper.readTree(content);
    }

    @Override
    public boolean canHandle(RepositoryEvent event) {
        return event instanceof WorkflowDispatchEvent;
    }
}
