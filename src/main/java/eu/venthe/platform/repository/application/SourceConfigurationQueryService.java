package eu.venthe.platform.repository.application;

import eu.venthe.platform.repository.domain.SourceConfiguration;
import eu.venthe.platform.repository.domain.SourceConfigurationVisitor;
import eu.venthe.platform.repository.domain.SourceConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceConfigurationQueryService {
    private final SourceConfigurationRepository sourceConfigurationRepository;

    public Optional<SourceConfigurationDto> getSourceInformation(String name) {
        return sourceConfigurationRepository.find(name).map(ConfigurationVisitor::toDto);
    }

    private static class ConfigurationVisitor implements SourceConfigurationVisitor {
        private final SourceConfigurationDto.SourceConfigurationDtoBuilder builder = SourceConfigurationDto.builder();

        @Override
        public void setName(String name) {
            builder.name(name);
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
