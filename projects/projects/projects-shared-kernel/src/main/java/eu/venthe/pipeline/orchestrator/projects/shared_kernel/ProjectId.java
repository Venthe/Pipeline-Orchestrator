package eu.venthe.pipeline.orchestrator.projects.shared_kernel;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectId {
    String systemId;
    String id;

    public String serialize() {
        return "%s/%s".formatted(systemId, id);
    }

    public static ProjectId from(String projectId) {
        int indexOfProject = projectId.indexOf("/");
        int indexOfProjectName = indexOfProject + 1;

        if (indexOfProject <= 0 || indexOfProjectName >= projectId.length()) {
            throw new IllegalArgumentException("Project name is required");
        }

        String systemId = projectId.substring(0, indexOfProject);

        String project = projectId.substring(indexOfProjectName);
        return ProjectId.of(systemId, project);
    }
}
