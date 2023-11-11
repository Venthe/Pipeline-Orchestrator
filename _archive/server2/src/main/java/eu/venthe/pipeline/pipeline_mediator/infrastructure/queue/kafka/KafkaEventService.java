package eu.venthe.pipeline.pipeline_mediator.infrastructure.queue.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.pipeline_mediator.plugins.gerrit.GerritBindingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "infrastructure.queue.type", havingValue = "kafka")
@Service
@RequiredArgsConstructor
public class KafkaEventService {
    private final GerritBindingConfiguration configuration;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public SendResult<String, Object> send(QueuedEvent object) {
        return kafkaTemplate.send(
                getTopic(),
                prepareObject(object)
        ).get();
    }

    private String prepareObject(QueuedEvent object) {
        return objectMapper.valueToTree(object).toString();
    }

    private String getTopic() {
        return configuration.getBinding().getTopic();
    }
}
