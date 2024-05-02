package eu.venthe.pipeline.orchestrator.infrastructure.in_memory_message_broker;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MessageBrokerTest {
    private MessageListener messageListener;
    private MessageBroker messageBroker;
    private Map<String, Boolean> results;

    @BeforeEach
    public void setup() {
        messageListener = new MessageListener();
        messageBroker = new MessageBroker(messageListener);
        results = new HashMap<>();
    }

    @Test
    void name() {
        registerListener("1", (id, envelope) -> log.info("Observer {}: {}", id, envelope));
        registerListener("2", (id, envelope) -> log.info("Observer {}: {}", id, envelope));
        registerListener("3", (id, envelope) -> log.info("Observer {}: {}", id, envelope));
        log.info("{}", results);

        messageBroker.publish(new EnvelopeImpl<>("Example data"));

        Awaitility.await().untilAsserted(() -> {
                    log.info("{}", results);
                    Assertions.assertThat(results)
                            .hasEntrySatisfying("1", e -> Assertions.assertThat(e).isTrue())
                            .hasEntrySatisfying("2", e -> Assertions.assertThat(e).isTrue())
                            .hasEntrySatisfying("3", e -> Assertions.assertThat(e).isTrue());
                }
        );
    }

    private void registerListener(String id, BiConsumer<String, Envelope<?>> listener) {
        results.put(id, false);
        messageListener.observe(String.class, message -> {
            listener.accept(id, message);
            results.put(id, true);
        });
    }
}
