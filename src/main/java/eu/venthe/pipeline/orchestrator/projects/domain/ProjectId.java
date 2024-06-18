package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectId {
    SourceConfigurationId configurationId;
    String name;

    public String serialize() {
        return "%s/%s".formatted(configurationId.id(), name);
    }

    public static ProjectId from(String projectId) {
        int indexOfProject = projectId.indexOf("/");
        int indexOfProjectName = indexOfProject + 1;

        if (indexOfProject <= 0 || indexOfProjectName >= projectId.length()) {
            throw new IllegalArgumentException("Project name is required");
        }

        var systemId = new SourceConfigurationId(projectId.substring(0, indexOfProject));

        var project = projectId.substring(indexOfProjectName);
        return ProjectId.of(systemId, project);
    }
}
