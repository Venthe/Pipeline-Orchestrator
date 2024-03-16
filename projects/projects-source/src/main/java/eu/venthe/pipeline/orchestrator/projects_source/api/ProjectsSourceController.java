package eu.venthe.pipeline.orchestrator.projects_source.api;

import eu.venthe.pipeline.orchestrator.plugins.projects.CreateProjectSourceConfigurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects-source-configurations")
public class ProjectsSourceController {
    private final ProjectsSourceConfigurationCommandService commandService;
    private final ProjectsSourceConfigurationQueryService queryService;

    @GetMapping("/list")
    public ResponseEntity<?> listConfigurations() {
        return ResponseEntity.ok(queryService.listConfigurations());
    }

    @GetMapping("/{projectSourceConfigurationId}")
    public ResponseEntity<?> getConfiguration(@PathVariable String projectSourceConfigurationId) {
        return ResponseEntity.ok(queryService.getConfiguration(projectSourceConfigurationId));
    }

    @PostMapping("/{projectSourceConfigurationId}/synchronize")
    public ResponseEntity<?> synchronizeProjects(@PathVariable String projectSourceConfigurationId) {
        commandService.synchronizeProjects(projectSourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> synchronizeProjects(@RequestBody CreateProjectSourceConfigurationDto configurationDto) {
        commandService.addProjectSourceConfiguration(configurationDto.getId(), configurationDto.getSourceType(), configurationDto.getProperties());

        return ResponseEntity.ok().build();
    }
}
