package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface WorkflowRunRepository extends DomainRepository<WorkflowRunRepository.Aggregate, WorkflowRunRepository.Id> {

    record Id(ProjectId projectId, WorkflowRunId workflowRunId) {
    }

    record Aggregate(Id id, WorkflowRun run) implements eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate<Id> {
        @Override
        public Id getId() {
            return id();
        }
    }
}
