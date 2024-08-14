package eu.venthe.platform.workflow.runs.infrastructure;

import eu.venthe.platform.workflow.runs.WorkflowRun;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface WorkflowRunRepository extends DomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> {

    record Id(ProjectId projectId, WorkflowRunId workflowRunId) {
    }

    record Aggregate(Id id, WorkflowRun run) implements eu.venthe.platform.shared_kernel.Aggregate<Id> {
        @Override
        public Id getId() {
            return id();
        }
    }
}
