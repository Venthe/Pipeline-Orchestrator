package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnBranches;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnInputs;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.WorkflowDispatchEvent;

public class WorkflowDispatchEventWrapper extends AbstractEventWrapper<WorkflowDispatchEvent> {
    public WorkflowDispatchEventWrapper(WorkflowDispatchEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnInputs onInputs) {
        return onInputs.match(getEvent().getInputs().getSimple());
    }

    @Override
    public Boolean matches(OnBranches onBranches) {
        return onBranches.match(getEvent().getRef());
    }
}
