package eu.venthe.platform.projects.api;

import eu.venthe.platform.projects.application.SourceConfigurationCommandService;
import eu.venthe.platform.projects.application.SourceConfigurationDto;
import eu.venthe.platform.projects.application.SourceConfigurationQueryService;
import eu.venthe.platform.projects.plugin.template.Repository;
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
    public String register(@RequestBody RegisterSpecification specification) {
        return sourceConfigurationCommandService.register(specification.name(), specification.sourceType(), specification.properties());
    }

    @GetMapping("/{name}")
    public Optional<SourceConfigurationDto> getSourceConfiguration(@PathVariable String name) {
        return sourceConfigurationQueryService.getSourceInformation(name);
    }

    @GetMapping("/{name}/projects")
    Set<Repository> getProjectsForSource(@PathVariable String name) {
        return sourceConfigurationQueryService.getAllRepositories(name);
    }

    public record RegisterSpecification(String name, String sourceType, Map<String, DynamicValue> properties) {
    }
}
