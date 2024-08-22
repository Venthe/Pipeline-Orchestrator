package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;

public record RequestJobRunCommand(RepositoryId repositoryId,
                                   WorkflowRunId workflowRunId,
                                   JobRunId runId) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
