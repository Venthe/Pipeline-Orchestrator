package eu.venthe.pipeline.workflows.runs.infrastructure;

import eu.venthe.pipeline.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.projects.domain.ProjectId;
import eu.venthe.pipeline.shared_kernel.DomainRepository;

public interface WorkflowRunRepository extends DomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> {

    record Id(ProjectId projectId, WorkflowRunId workflowRunId) {
    }

    record Aggregate(Id id, WorkflowRun run) implements eu.venthe.pipeline.shared_kernel.Aggregate<Id> {
        @Override
        public Id getId() {
            return id();
        }
    }
}
