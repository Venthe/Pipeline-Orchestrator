package eu.venthe.pipeline.orchestrator.projects.api;

import eu.venthe.pipeline.orchestrator.plugins.ProjectProvider;
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
    ResponseEntity<Collection<String>> getProjects() {
        Collection<String> projects = projectProvider.getProjects();
        return ResponseEntity.ok(projects);
    }
}
