package eu.venthe.pipeline.orchestrator.projects.domain.events;

import eu.venthe.pipeline.orchestrator.projects.api.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnInputs;

public class WorkflowDispatchEventWrapper extends AbstractEventWrapper<WorkflowDispatchEvent> {
    public WorkflowDispatchEventWrapper(WorkflowDispatchEvent event) {
        super(event);
    }

    @Override
    public Boolean matches(OnInputs onInputs) {
        return onInputs.match(getEvent().getInputs().getAdditionalProperties());
    }

    @Override
    public String getId() {
        return getEvent().getId().toString();
    }

    @Override
    public String getType() {
        return getEvent().getType();
    }
}
