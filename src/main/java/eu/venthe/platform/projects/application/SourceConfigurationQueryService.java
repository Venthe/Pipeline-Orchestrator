package eu.venthe.platform.projects.application;

import eu.venthe.platform.projects.domain.ManagedRepository;
import eu.venthe.platform.projects.domain.SourceConfiguration;
import eu.venthe.platform.projects.domain.SourceConfigurationRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationVisitor;
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

    public Optional<SourceConfigurationDto> getSourceInformation(String sourceIdentifier) {
        return sourceConfigurationRepository.find(sourceIdentifier).map(ConfigurationVisitor::toDto);
    }

    public Set<ManagedRepository> getAllRepositories(String sourceIdentifier) {
        return sourceConfigurationRepository.find(sourceIdentifier)
                .orElseThrow(() -> new SourceConfigurationNotFoundException(sourceIdentifier))
                .getAllRepositories();
    }

    public Optional<ManagedRepository> getRepository(String sourceIdentifier, String repositoryName) {
        return sourceConfigurationRepository.find(sourceIdentifier)
                .orElseThrow(() -> new SourceConfigurationNotFoundException(sourceIdentifier))
                .getRepository(repositoryName);
    }

    private static class ConfigurationVisitor implements SourceConfigurationVisitor {
        private final SourceConfigurationDto.SourceConfigurationDtoBuilder builder = SourceConfigurationDto.builder();

        @Override
        public void setIdentifier(String identifier) {
            builder.identifier(identifier);
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
