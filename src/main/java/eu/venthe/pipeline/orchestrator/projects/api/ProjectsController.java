/*
package eu.venthe.pipeline.orchestrator.projects.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectsQueryService queryService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(queryService.listProjects());
    }
}
*/
