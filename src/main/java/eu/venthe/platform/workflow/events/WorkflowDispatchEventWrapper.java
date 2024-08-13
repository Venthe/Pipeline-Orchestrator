package eu.venthe.platform.workflow.events;

import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnWorkflowDispatchInputs;
import eu.venthe.platform.workflow.definition.contexts.on.matchers.OnBranches;
import eu.venthe.platform.shared_kernel.system_events.WorkflowDispatchEvent;

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
        return onBranches.match(getEvent().getRevision());
    }
}
