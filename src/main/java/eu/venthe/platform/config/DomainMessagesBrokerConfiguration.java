package eu.venthe.platform.config;

import eu.venthe.platform.infrastructure.StubDomainMessagesBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainMessagesBrokerConfiguration {
    @Bean
    StubDomainMessagesBroker domainMessagesBroker() {
        return new StubDomainMessagesBroker();
    }
}
