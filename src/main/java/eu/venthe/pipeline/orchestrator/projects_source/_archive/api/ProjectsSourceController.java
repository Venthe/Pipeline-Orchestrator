/*
package eu.venthe.pipeline.orchestrator.projects_source._archive.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/projects-source-configurations")
public class ProjectsSourceController {
    private final ProjectsSourceConfigurationCommandService commandService;
    private final ProjectsSourceConfigurationQueryService queryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<?> listConfigurations() {
        return ResponseEntity.ok(queryService.listConfigurations());
    }

    @GetMapping("/{projectSourceConfigurationId}")
    public ResponseEntity<?> getConfiguration(@PathVariable String projectSourceConfigurationId) {
        return ResponseEntity.ok(queryService.getConfiguration(projectSourceConfigurationId));
    }

    @DeleteMapping("/{projectSourceConfigurationId}")
    public ResponseEntity<?> removeProjectSourceConfiguration(@PathVariable String projectSourceConfigurationId) {
        commandService.removeProjectSourceConfiguration(projectSourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectSourceConfigurationId}/synchronize")
    public ResponseEntity<?> synchronizeProject(@PathVariable("projectSourceConfigurationId") String projectSourceConfigurationId) {
        commandService.synchronizeProject(projectSourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/synchronize")
    public ResponseEntity<?> synchronizeProjects() {
        commandService.synchronizeProjects();

        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> addProjectSourceConfiguration(@RequestBody ObjectNode configurationDto) {
        commandService.addProjectSourceConfiguration(
                configurationDto.get("configurationId").asText(),
                configurationDto.get("systemType").asText(),
                Map.of()
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping("/system-type/list")
    public Object listSystemTypes() {
        return queryService.listSystemTypes();
    }

    @GetMapping("/system-type/{systemType}")
    public ResponseEntity<?> getPlugin(@PathVariable("systemType") String systemType) {
        return ResponseEntity.of(queryService.getPluginDefinition(systemType)
                .map(objectMapper::valueToTree));
    }
}
*/
