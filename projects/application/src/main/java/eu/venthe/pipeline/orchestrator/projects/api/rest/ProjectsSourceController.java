package eu.venthe.pipeline.orchestrator.projects.api.rest;

import eu.venthe.pipeline.orchestrator.projects.application.ProjectsSourceConfigurationService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectSourceConfigurationId;
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
