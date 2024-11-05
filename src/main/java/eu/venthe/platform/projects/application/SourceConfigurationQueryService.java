package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.SourceConfiguration;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationVisitor;
import eu.venthe.platform.projects.plugin.template.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceConfigurationQueryService {
    private final SourceConfigurationRepository sourceConfigurationRepository;

    public Optional<SourceConfigurationDto> getSourceInformation(String name) {
        return sourceConfigurationRepository.find(name).map(ConfigurationVisitor::toDto);
    }

    public Set<Repository> getAllRepositories(String sourceName) {
        return sourceConfigurationRepository.find(sourceName)
                .orElseThrow(() -> new SourceConfigurationNotFoundException(sourceName))
                .getAllRepositories();
    }

    private static class ConfigurationVisitor implements SourceConfigurationVisitor {
        private final SourceConfigurationDto.SourceConfigurationDtoBuilder builder = SourceConfigurationDto.builder();

        @Override
        public void setName(String name) {
            builder.name(name);
        }

        @Override
        public void setType(String type) {
            builder.type(type);
        }

        public SourceConfigurationDto build() {
            return builder.build();
        }

        private static SourceConfigurationDto toDto(SourceConfiguration sourceConfiguration) {
            var visitor = new ConfigurationVisitor();
            sourceConfiguration.visit(visitor);
            return visitor.build();
        }
    }
}
