package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import eu.venthe.pipeline.gerrit.model.ProjectInfo;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class GerritProjectProvider implements ProjectProvider {
    private final ProjectsApi projectsApi;

    public GerritProjectProvider(RestTemplate restTemplate, ObjectMapper objectMapper, Map<String, String> properties) {
        ApiClient apiClient = new ApiClient(objectMapper, restTemplate);
        apiClient.setBasePath(properties.get("basePath"));
        apiClient.setUsername(properties.get("username"));
        apiClient.setPassword(properties.get("password"));
        projectsApi = new ProjectsApi(apiClient);
    }

    @Override
    public Collection<ProjectDto> getProjects() {
        return getListProjects().keySet().stream()
                .map(projectId -> new ProjectDto(ProjectDto.Status.ACTIVE, projectId))
                .collect(Collectors.toSet());
    }

    private Map<String, ProjectInfo> getListProjects() {
        return projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }
}
