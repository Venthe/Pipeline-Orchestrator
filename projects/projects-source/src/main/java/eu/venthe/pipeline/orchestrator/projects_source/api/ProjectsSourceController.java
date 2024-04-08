package eu.venthe.pipeline.orchestrator.projects_source.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.StreamSupport;

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

    @PostMapping("/{projectSourceConfigurationId}/synchronize")
    public ResponseEntity<?> synchronizeProjects(@PathVariable("projectSourceConfigurationId") String projectSourceConfigurationId) {
        commandService.synchronizeProjects(projectSourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> synchronizeProjects(@RequestBody ObjectNode configurationDto) {
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
