package eu.venthe.pipeline.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class, SpringExtension.class})
@Testcontainers
public abstract class AbstractIntegrationTest {
    @InjectSoftAssertions
    protected SoftAssertions sa;

    @Autowired
    protected ObjectMapper mapper;

    @LocalServerPort
    protected long port;

    protected RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder().baseUrl(getBaseUrl()).build();
    }

    @NonNull String getBaseUrl() {
        return "http://localhost:" + port;
    }
}
