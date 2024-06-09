package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.OnWorkflowDispatchInputs;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.matchers.OnBranches;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;

public class WorkflowDispatchEventWrapper extends AbstractEventWrapper<WorkflowDispatchEvent> {
    public WorkflowDispatchEventWrapper(WorkflowDispatchEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnWorkflowDispatchInputs onWorkflowDispatchInputs) {
        return onWorkflowDispatchInputs.match(getEvent().getInputs().getSimple());
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getEvent().getRef());
    }
}
