package eu.venthe.pipeline.application.configuration;

import io.cucumber.spring.ScenarioScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class DependenciesConfiguration {
    @Bean
    @ScenarioScope
    RestClient restClient() {
        return RestClient.create();
    }
}
