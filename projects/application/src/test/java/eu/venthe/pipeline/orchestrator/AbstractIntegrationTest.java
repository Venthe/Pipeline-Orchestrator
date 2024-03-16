package eu.venthe.pipeline.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
@Testcontainers
public abstract class AbstractIntegrationTest {
    @InjectSoftAssertions
    protected SoftAssertions sa;

    @Autowired
    protected ObjectMapper mapper;
}
