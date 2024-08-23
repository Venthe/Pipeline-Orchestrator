package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import lombok.Builder;

@Builder
public record WorkflowRunCreatedEvent(
        OrganizationName organizationName,
        RepositoryName repositoryName,
        WorkflowRunId workflowRunId
) implements DomainTrigger {
    @Override
    public String getType() {
        return "workflow_run_created";
    }
}
