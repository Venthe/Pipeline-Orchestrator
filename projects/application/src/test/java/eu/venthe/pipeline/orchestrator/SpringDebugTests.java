package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(classes = {DomainMessageBroker.class})
@SpringBootTest(properties = {
        "debug=true",
        "logging.level.org.springframework=DEBUG"
})
public class SpringDebugTests {
    @Test
    void contextLoads() {
    }
}
