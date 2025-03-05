package eu.venthe.platform.projects.api;

import eu.venthe.platform.projects.application.SourceConfigurationCommandService;
import eu.venthe.platform.projects.application.SourceConfigurationDto;
import eu.venthe.platform.projects.application.SourceConfigurationQueryService;
import eu.venthe.platform.projects.domain.ManagedRepository;
import eu.venthe.platform.projects.domain.SourceConfigurationInternalIdentifier;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/source-configuration")
@RequiredArgsConstructor
public class SourceConfigurationController {
    private final SourceConfigurationCommandService sourceConfigurationCommandService;
    private final SourceConfigurationQueryService sourceConfigurationQueryService;

    @PostMapping
    public SourceConfigurationInternalIdentifier register(@RequestBody RegisterSpecification specification) {
        return sourceConfigurationCommandService.register(specification.identifier(), specification.sourceType(), specification.properties());
    }

    @GetMapping("/{sourceIdentifier}")
    public Optional<SourceConfigurationDto> getSourceConfiguration(@PathVariable SourceConfigurationInternalIdentifier sourceIdentifier) {
        return sourceConfigurationQueryService.getSourceInformation(sourceIdentifier);
    }

    @GetMapping("/{sourceIdentifier}/projects")
    Set<ManagedRepository> getProjectsForSource(@PathVariable SourceConfigurationInternalIdentifier sourceIdentifier) {
        return sourceConfigurationQueryService.getAllRepositories(sourceIdentifier);
    }

    public record RegisterSpecification(
            SourceConfigurationInternalIdentifier identifier,
            String sourceType,
            Map<String, DynamicValue> properties
    ) {
    }
}
