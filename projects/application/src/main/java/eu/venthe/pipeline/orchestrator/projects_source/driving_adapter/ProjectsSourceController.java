package eu.venthe.pipeline.orchestrator.projects_source.driving_adapter;

import eu.venthe.pipeline.orchestrator.projects_source.core.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfigurationId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects-source-configurations")
public class ProjectsSourceController {
    private final ProjectsSourceConfigurationService projectsSourceConfigurationService;

    @GetMapping("/list")
    public ResponseEntity<?> listConfigurations() {
        return ResponseEntity.ok(projectsSourceConfigurationService.listConfigurations());
    }

    @GetMapping("/{projectSourceConfigurationId}")
    public ResponseEntity<?> getConfiguration(@PathVariable ProjectSourceConfigurationId projectSourceConfigurationId) {
        return ResponseEntity.ok(projectsSourceConfigurationService.getConfiguration(projectSourceConfigurationId));
    }

    @PostMapping("/{projectSourceConfigurationId}/synchronize")
    public ResponseEntity<?> synchronizeProjects(@PathVariable ProjectSourceConfigurationId projectSourceConfigurationId) {
        projectsSourceConfigurationService.synchronizeProjects(projectSourceConfigurationId);

        return ResponseEntity.ok().build();
    }
}
