package eu.venthe.pipeline.orchestrator;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = {
        "debug=true",
        "logging.level.org.springframework=DEBUG"
})
class SpringDebugTests extends AbstractIntegrationTest {
    @Test
    void contextLoads() {
    }
}
