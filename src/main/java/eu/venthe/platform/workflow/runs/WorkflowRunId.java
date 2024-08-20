package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.project.domain.ProjectId;
import lombok.NonNull;

import java.util.UUID;

public record WorkflowRunId(@NonNull ProjectId projectId, @NonNull String value) {
    public static WorkflowRunId generate(ProjectId projectId) {
        return new WorkflowRunId(projectId, UUID.randomUUID().toString());
    }
}
