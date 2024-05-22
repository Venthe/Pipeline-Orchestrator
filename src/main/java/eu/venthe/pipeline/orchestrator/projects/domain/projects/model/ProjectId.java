package eu.venthe.pipeline.orchestrator.projects.domain.projects.model;

import eu.venthe.pipeline.orchestrator.projects.domain.model.SourceType;
import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectId {
    SourceType sourceType;
    String id;

    public String serialize() {
        return "%s/%s".formatted(sourceType, id);
    }

    public static ProjectId from(String projectId) {
        int indexOfProject = projectId.indexOf("/");
        int indexOfProjectName = indexOfProject + 1;

        if (indexOfProject <= 0 || indexOfProjectName >= projectId.length()) {
            throw new IllegalArgumentException("Project name is required");
        }

        var systemId = new SourceType(projectId.substring(0, indexOfProject));

        var project = projectId.substring(indexOfProjectName);
        return ProjectId.of(systemId, project);
    }
}
