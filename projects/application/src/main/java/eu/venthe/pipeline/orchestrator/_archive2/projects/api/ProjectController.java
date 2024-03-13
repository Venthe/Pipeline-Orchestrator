package eu.venthe.pipeline.orchestrator._archive2.projects.api;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectProvider projectProvider;

    @GetMapping("")
    ResponseEntity<Collection<ProjectDto>> getProjects() {
        Collection<ProjectDto> projects = projectProvider.getProjects();
        return ResponseEntity.ok(projects);
    }
}
