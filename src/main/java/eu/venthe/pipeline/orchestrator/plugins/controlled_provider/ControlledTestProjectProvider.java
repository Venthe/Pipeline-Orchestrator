package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.projects.ProjectStatus;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@RequiredArgsConstructor
public class ControlledTestProjectProvider implements ProjectProvider {
    private final Set<ProjectDto> projects = new HashSet<>();
    private final RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public Collection<ProjectDto> getProjects() {
        return Stream.of(
                        loadProjectsFromResourcesDirectory("/projects"),
                        loadProjectsFromStaticSource(),
                        loadProjectsFromWebServer("localhost:4545")
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<ProjectDto> loadProjectsFromWebServer(String s) {
        ResponseEntity<JsonNode> forEntity = restTemplate.getForEntity("http://localhost:4545", JsonNode.class);
        JsonNode body = forEntity.getBody();
        return StreamSupport.stream(body.spliterator(), true)
                .filter(n -> n.get("type").asText().equalsIgnoreCase("directory"))
                .map(n -> new ProjectDto(eu.venthe.pipeline.orchestrator.shared_kernel.projects.ProjectStatus.PUBLIC, n.get("path").asText()))
                .collect(Collectors.toSet());
    }

    private Set<ProjectDto> loadProjectsFromStaticSource() {
        return projects;
    }

    private Set<ProjectDto> loadProjectsFromResourcesDirectory(String name) throws URISyntaxException, IOException {
        URL resource = getClass().getResource(name);
        if (resource == null) {
            return Collections.emptySet();
        }
        Path path = Paths.get(resource.toURI());
        return Files.list(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(id -> new ProjectDto(ProjectStatus.PUBLIC, id))
                .collect(Collectors.toSet());
    }

    public void addProject(ProjectDto project) {
        projects.add(project);
    }

    public void updateProject(ProjectDto project) {
        projects.add(project);
    }

    public void deleteProject(String id) {
        projects.removeIf(project -> project.getId().equals(id));
    }
}
