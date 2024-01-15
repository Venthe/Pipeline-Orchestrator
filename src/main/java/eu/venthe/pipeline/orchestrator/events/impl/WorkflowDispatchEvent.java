package eu.venthe.pipeline.orchestrator.events.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.AbstractHandledEvent;
import eu.venthe.pipeline.orchestrator.events.contexts.*;
import eu.venthe.pipeline.orchestrator.events.contexts.common.RefContext;
import eu.venthe.pipeline.orchestrator.events.contexts.definitions.Repository;
import eu.venthe.pipeline.orchestrator.events.model.EventType;
import eu.venthe.pipeline.orchestrator.workflows.contexts.on.OnInputs;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class WorkflowDispatchEvent extends AbstractHandledEvent {
    // private final Optional<EnterpriseContext> enterprise;
    // private final Optional<Organization> organization;
    // private final Optional<InstallationLite> installation;
    private final InputsContext inputs;
    private final String ref;
    private final Repository repository;
    private final String workflow;

    public WorkflowDispatchEvent(ObjectNode root) {
        super(root, EventType.WORKFLOW_DISPATCH);

        inputs = InputsContext.ensure(this.root.get("inputs"));
        ref = RefContext.ensure(this.root.get("ref"));
        repository = RepositoryContext.ensure(root.get("repository"));
        workflow = WorkflowContext.ensure(this.root.get("workflow"));
    }

    @Override
    public Boolean matches(OnInputs onInputs) {
        return onInputs.match(inputs);
    }
}
