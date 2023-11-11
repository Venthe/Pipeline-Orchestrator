package eu.venthe.pipeline.pipeline_mediator;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.venthe.pipeline.pipeline_mediator.configuration.YamlMapper;
import eu.venthe.pipeline.pipeline_mediator.domain.model.events.WorkflowTriggerEventType;
import eu.venthe.pipeline.pipeline_mediator.domain.plugins.MediatorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.node.ObjectNode;

@Slf4j
class LocalTest extends BaseTest {
    @Autowired
    MediatorService mediatorService;

    @Autowired
    YamlMapper yamlMapper;

    @Test
    void workflowDispatchEventTest() throws JsonProcessingException {
        var workflowDispatchEvent = event("""
                type: %s
                
                """.stripIndent(), WorkflowTriggerEventType.WORKFLOW_DISPATCH.getValue());

      log.info("{}", workflowDispatchEvent);
    }

    private ObjectNode event(String content, Object... params) throws JsonProcessingException {
        return yamlMapper.getObjectMapper().readValue(String.format(content, params), ObjectNode.class);
    }
}
