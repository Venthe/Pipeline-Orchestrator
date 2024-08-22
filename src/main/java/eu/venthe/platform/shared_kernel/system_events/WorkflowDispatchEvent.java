package eu.venthe.platform.shared_kernel.system_events;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.system_events.contexts.WorkflowDispatchInputsContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.PathContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.SimpleRevisionContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.EventType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.nio.file.Path;

/**
 * This event occurs when a GitHub Actions workflow is manually triggered. For more information, see "Manually running a
 * workflow."
 * <p>
 * For activity relating to workflow runs, use the workflow_run event.
 */
@Getter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
public class WorkflowDispatchEvent extends AbstractRepositoryEvent {
    private final WorkflowDispatchInputsContext inputs;
    private final SimpleRevision revision;
    private final Path workflow;

    public WorkflowDispatchEvent(ObjectNode _root) {
        super(_root);
        var root = ContextUtilities.validateIsObjectNode(_root);

        inputs = WorkflowDispatchInputsContext.create(root.get("inputs"));
        revision = SimpleRevisionContext.ensure(root.get("revision"));
        workflow = PathContext.ensure(root.get("workflow"));
    }

    public EventType getType() {
        return EventType.WORKFLOW_DISPATCH;
    }
}
