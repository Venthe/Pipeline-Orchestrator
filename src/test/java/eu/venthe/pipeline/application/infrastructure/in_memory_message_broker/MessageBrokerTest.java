package eu.venthe.pipeline.application.infrastructure.in_memory_message_broker;

import eu.venthe.pipeline.shared_kernel.events.Envelope;
import eu.venthe.pipeline.shared_kernel.events.MessageBroker;
import eu.venthe.pipeline.shared_kernel.events.MessageListenerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
class MessageBrokerTest {
    private MessageBroker broker;
    private MessageListenerRegistry registry;
    private Map<String, Boolean> results;

    @BeforeEach
    public void setup() {
        var broker = new InMemoryMessageBroker();
        this.broker = broker;
        registry = broker;
        results = new HashMap<>();
    }

    @Test
    void name() {
        registerListener("1", (id, envelope) -> log.info("Observer 1 - {}: {}", id, envelope));
        registerListener("2", (id, envelope) -> log.info("Observer 2 - {}: {}", id, envelope));
        registerListener("3", (id, envelope) -> log.info("Observer 3 - {}: {}", id, envelope));
        log.info("{}", results);

        broker.exchange(new Envelope<>("Example data"));

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
        registry.register(String.class, message -> {
            listener.accept(id, message);
            results.put(id, true);
        });
    }
}
