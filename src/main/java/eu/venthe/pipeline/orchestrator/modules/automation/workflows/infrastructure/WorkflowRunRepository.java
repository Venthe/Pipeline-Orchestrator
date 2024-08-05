package eu.venthe.pipeline.orchestrator.modules.automation.workflows.infrastructure;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

public interface WorkflowRunRepository extends DomainRepository<WorkflowRun, WorkflowRunId> {
}
