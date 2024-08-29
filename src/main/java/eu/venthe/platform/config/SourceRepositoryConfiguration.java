package eu.venthe.platform.config;

import eu.venthe.platform.infrastructure.InMemoryRepository;
import eu.venthe.platform.repository.domain.SourceConfiguration;
import eu.venthe.platform.repository.domain.SourceConfigurationRepository;
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
            public boolean exists(String name) {
                return repository.exists(name);
            }

            @Override
            public void save(SourceConfiguration sourceConfiguration) {
                repository.save(sourceConfiguration.getName(), sourceConfiguration);
            }

            @Override
            public Optional<SourceConfiguration> find(String name) {
                return repository.find(name);
            }
        };
    }
}
