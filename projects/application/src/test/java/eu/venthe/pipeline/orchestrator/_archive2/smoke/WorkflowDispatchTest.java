/*
package eu.venthe.pipeline.orchestrator._archive2.smoke;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import eu.venthe.pipeline.orchestrator.projects.domain.application.WorkflowTriggerService;
import eu.venthe.pipeline.orchestrator.projects.domain.events.TriggerEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.infrastructure.WorkflowExecutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static eu.venthe.pipeline.orchestrator._archive2.workflow_executions.WorkflowExecutionStatus.COMPLETED;
import static java.time.Duration.ofSeconds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@Slf4j
class WorkflowDispatchTest extends AbstractIntegrationTest {
    @Autowired
    WorkflowTriggerService workflowTriggerService;
//    @Autowired
//    TestVersionControlSystem testVersionControlSystem;
    @Autowired
    WorkflowExecutionRepository workflowExecutionRepository;
    YAMLMapper yamlMapper = null;

    @Test
    void _1() throws JsonProcessingException {
        TriggerEvent event = mapEvent("""
                type: workflow_dispatch
                workflow: .pipeline/workflows/example.yaml
                ref: "refs/heads/main"
                repository:
                  repositoryId: gerrit
                  project: Example-Project
                """);

//        testVersionControlSystem.addFile(new TestVersionControlSystem.FileRef("TEST", "Example-Project", "main", ".pipeline/workflows/example.yaml"), """
//                on:
//                  workflow_dispatch: ~
//                jobs:
//                  test: ~
//                """);

        String workflowExecutionId = workflowTriggerService.trigger(event).orElseThrow();

        await().atMost(ofSeconds(10))
                .untilAsserted(() -> assertThat(workflowExecutionRepository.get(workflowExecutionId))
                        .isNotEmpty()
                        .hasValueSatisfying(v -> {
                            assertThat(v.getStatus())
                                    .isEqualTo(COMPLETED);
                        })
                );
    }

    private TriggerEvent mapEvent(String eventContent) throws JsonProcessingException {
        ObjectNode rawEvent = (ObjectNode) yamlMapper.readTree(eventContent);
        rawEvent.set("id", yamlMapper.getNodeFactory().textNode(UUID.randomUUID().toString()));
        return new TriggerEvent(rawEvent);
    }
}
*/
