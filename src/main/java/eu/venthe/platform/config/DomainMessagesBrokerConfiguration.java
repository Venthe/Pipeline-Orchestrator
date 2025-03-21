package eu.venthe.platform.config;

import eu.venthe.platform.infrastructure.InMemoryDomainMessageBroker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainMessagesBrokerConfiguration {
    @Bean
    InMemoryDomainMessageBroker inMemoryDomainMessageBroker() {
        return new InMemoryDomainMessageBroker();
    }
}
