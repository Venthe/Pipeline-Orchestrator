package eu.venthe.pipeline.orchestrator.projects.projects.domain.model;

import eu.venthe.pipeline.orchestrator.projects.source_configuration.domain.model.ProjectsSourceConfigurationId;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectId {
    ProjectsSourceConfigurationId configurationId;
    String name;

    public String serialize() {
        return "%s/%s".formatted(configurationId, name);
    }

    public static ProjectId from(String projectId) {
        int indexOfProject = projectId.indexOf("/");
        int indexOfProjectName = indexOfProject + 1;

        if (indexOfProject <= 0 || indexOfProjectName >= projectId.length()) {
            throw new IllegalArgumentException("Project name is required");
        }

        var systemId = new ProjectsSourceConfigurationId(projectId.substring(0, indexOfProject));

        var project = projectId.substring(indexOfProjectName);
        return ProjectId.of(systemId, project);
    }
}
