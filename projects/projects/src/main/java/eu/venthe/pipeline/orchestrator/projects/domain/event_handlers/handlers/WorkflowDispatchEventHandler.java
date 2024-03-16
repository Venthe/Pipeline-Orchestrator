package eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import eu.venthe.pipeline.orchestrator.projects.application.WorkflowExecutionRepository;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.TypedEventHandler;
import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.events.impl.WorkflowDispatchHandledEvent;
import eu.venthe.pipeline.orchestrator.projects.api.version_control.common.EventType;
import eu.venthe.pipeline.orchestrator.plugins.job_executors.JobExecutor;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.workflow_executions.WorkflowExecution;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.Workflow;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WorkflowDispatchEventHandler implements TypedEventHandler {
    private final VersionControlSystemProvider versionControlSystem;
    private final JobExecutor jobExecutor;
    private final WorkflowExecutionRepository workflowExecutionRepository;
    private final YAMLMapper yamlMapper;

    @Override
    public Optional<String> handle(TriggerEvent event) {
        WorkflowDispatchHandledEvent workflowDispatchEvent = event.specify(WorkflowDispatchHandledEvent::new);

        log.info("Event triggers single workflow on path {}", workflowDispatchEvent.getWorkflow());

        Workflow workflow = loadWorkflow(workflowDispatchEvent)
                .map(data -> new String(data, StandardCharsets.UTF_8))
                .map(this::parseYaml)
                .map(ObjectNode.class::cast)
                .map(root -> new Workflow(root, null))
                .orElseThrow();

        log.trace("Workflow loaded {}", workflow);

        Optional<WorkflowExecution> workflowExecution = WorkflowExecution.from(workflow, workflowDispatchEvent);
        workflowExecution.ifPresentOrElse(we -> {
            we.start(jobExecutor);

            workflowExecutionRepository.save(we);
        }, () -> log.debug("No workflow execution is created"));

        return workflowExecution.map(WorkflowExecution::getId);
    }

    private Optional<byte[]> loadWorkflow(WorkflowDispatchHandledEvent workflowDispatchEvent) {
        return versionControlSystem.getFile(
                workflowDispatchEvent.getRepository().getName(),
                workflowDispatchEvent.getRef(),
                workflowDispatchEvent.getWorkflow()
        );
    }

    @SneakyThrows
    private JsonNode parseYaml(String data) {
        return yamlMapper.readTree(data);
    }

    @Override
    public EventType discriminator() {
        return EventType.WORKFLOW_DISPATCH;
    }
}
