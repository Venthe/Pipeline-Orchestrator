package eu.venthe.platform.workflow.runs.infrastructure;

import eu.venthe.platform.workflow.runs.WorkflowRun;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.shared_kernel.DomainRepository;

public interface WorkflowRunRepository extends DomainRepository<WorkflowRun, WorkflowRunId> {
}
