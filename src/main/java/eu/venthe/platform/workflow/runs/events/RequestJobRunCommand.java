package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.definition.contexts.JobName;
import eu.venthe.platform.workflow.runs.WorkflowRunId;

public record RequestJobRunCommand(OrganizationName organizationName,
                                   RepositoryName repositoryName,
                                   WorkflowRunId workflowRunId,
                                   JobName jobName,
                                   int runAttempt) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }
}
