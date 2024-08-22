/*
package eu.venthe.pipeline.orchestrator.repositorys._archive.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repositorys-source-configurations")
public class RepositorySourceController {
    private final RepositorySourceConfigurationCommandService commandService;
    private final RepositorySourceConfigurationQueryService queryService;
    private final ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<?> listConfigurations() {
        return ResponseEntity.ok(queryService.listConfigurations());
    }

    @GetMapping("/{repositorySourceConfigurationId}")
    public ResponseEntity<?> getConfiguration(@PathVariable String repositorySourceConfigurationId) {
        return ResponseEntity.ok(queryService.getConfiguration(repositorySourceConfigurationId));
    }

    @DeleteMapping("/{repositorySourceConfigurationId}")
    public ResponseEntity<?> removeRepositorySourceConfiguration(@PathVariable String repositorySourceConfigurationId) {
        commandService.removeRepositorySourceConfiguration(repositorySourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{repositorySourceConfigurationId}/synchronize")
    public ResponseEntity<?> synchronizeRepository(@PathVariable("repositorySourceConfigurationId") String repositorySourceConfigurationId) {
        commandService.synchronizeRepository(repositorySourceConfigurationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/synchronize")
    public ResponseEntity<?> synchronizeRepository() {
        commandService.synchronizeRepository();

        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> addRepositorySourceConfiguration(@RequestBody ObjectNode configurationDto) {
        commandService.addRepositorySourceConfiguration(
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
