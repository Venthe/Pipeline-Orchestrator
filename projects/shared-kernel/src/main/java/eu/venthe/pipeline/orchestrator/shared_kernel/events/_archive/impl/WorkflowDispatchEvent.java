package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.RepositoryContext;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.WorkflowContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.common.InputsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.common.RefContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.EventType;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.AbstractProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.definitions.Repository;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnInputs;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Getter
public class WorkflowDispatchEvent extends AbstractProjectEvent {
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
