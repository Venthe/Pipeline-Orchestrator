package eu.venthe.platform.config;

import eu.venthe.platform.infrastructure.InMemoryRepository;
import eu.venthe.platform.projects.domain.SourceConfiguration;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class SourceRepositoryConfiguration {
    @Bean
    SourceConfigurationRepository sourceConfigurationRepository() {
        return new SourceConfigurationRepository() {
            private final InMemoryRepository<String, SourceConfiguration> repository = new InMemoryRepository<>();

            @Override
            public boolean exists(String identifier) {
                return repository.exists(identifier);
            }

            @Override
            public void save(SourceConfiguration sourceConfiguration) {
                repository.save(sourceConfiguration.getIdentifier(), sourceConfiguration);
            }

            @Override
            public Optional<SourceConfiguration> find(String identifier) {
                return repository.find(identifier);
            }
        };
    }
}
