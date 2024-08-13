package eu.venthe.pipeline.application.fixtures;

import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Getter
@Component
@ScenarioScope
public class WebFixture {
    public WebFixture(@LocalServerPort Integer port, @Autowired RestClient restClient) {
        this.port = port;
        this.restClient = restClient;
    }

    private final Integer port;
    private final RestClient restClient;
}
