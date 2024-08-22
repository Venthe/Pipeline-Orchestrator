package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.repository.domain.RepositoryId;
import lombok.NonNull;

import java.util.UUID;

public record WorkflowRunId(@NonNull RepositoryId repositoryId, @NonNull String value) {
    public static WorkflowRunId generate(RepositoryId repositoryId) {
        return new WorkflowRunId(repositoryId, UUID.randomUUID().toString());
    }
}
