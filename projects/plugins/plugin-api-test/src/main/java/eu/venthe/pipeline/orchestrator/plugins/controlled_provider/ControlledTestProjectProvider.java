package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ControlledTestProjectProvider implements ProjectProvider {
    private final Set<ProjectDto> projects = new HashSet<>();

    @SneakyThrows
    @Override
    public Collection<ProjectDto> getProjects() {
        return Stream.of(
                        loadProjectsFromResourcesDirectory("/projects"),
                        loadProjectsFromStaticSource()
                )
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private Set<ProjectDto> loadProjectsFromStaticSource() {
        return projects;
    }

    private Set<ProjectDto> loadProjectsFromResourcesDirectory(String name) throws URISyntaxException, IOException {
        URL resource = getClass().getResource(name);
        Path path = Paths.get(resource.toURI());
        return Files.list(path)
                .map(Path::getFileName)
                .map(Path::toString)
                .map(id -> new ProjectDto(ProjectDto.Status.ACTIVE, id))
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
