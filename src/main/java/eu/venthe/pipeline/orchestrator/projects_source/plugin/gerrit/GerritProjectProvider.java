package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.invoker.ApiClient;
import eu.venthe.pipeline.gerrit.model.ProjectInfo;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectsProvider;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit.GerritHeaders.getTraceId;
import static java.util.stream.Collectors.toSet;

public class GerritProjectProvider implements ProjectsProvider {
    private final ProjectsApi projectsApi;

    public GerritProjectProvider(ApiClient apiClient) {
        projectsApi = new ProjectsApi(apiClient);
    }

    @Override
    public Collection<ProjectDto> getProjects() {
        return getListProjects().entrySet().stream()
                .filter(project -> Objects.equals(project.getValue().getState(), ProjectInfo.StateEnum.HIDDEN))
                .map(project -> new ProjectDto(project.getKey(), statusMapper(project.getValue().getState())))
                .collect(toSet());
    }

    private Map<String, ProjectInfo> getListProjects() {
        return projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, getTraceId());
    }

    private static ProjectStatus statusMapper(ProjectInfo.StateEnum stateEnum) {
        return switch (stateEnum) {
            case ACTIVE -> ProjectStatus.ACTIVE;
            case READ_ONLY -> ProjectStatus.ARCHIVED;
            default -> throw new UnsupportedOperationException();
        };
    }
}
