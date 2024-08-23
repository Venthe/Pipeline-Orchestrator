package eu.venthe.platform.workflow.definition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.workflow.data_interpolation.Expression;
import eu.venthe.platform.workflow.definition.contexts.*;
import eu.venthe.platform.workflow.definition.contexts.on.WorkflowDefinitionOnContext;
import eu.venthe.platform.workflow.events.EventWrapper;
import eu.venthe.platform.shared_kernel.system_events.SystemEvent;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Getter
@ToString(onlyExplicitlyIncluded = true)
@SuperBuilder
public class WorkflowDefinition {
    @Nullable
    private final String name;
    @Nullable
    private final Expression runName;
    private final WorkflowDefinitionOnContext on;
    @Nullable
    private final WorkflowDefinitionPermissionsContext permissions;
    @Nullable
    private final WorkflowDefinitionEnvironmentContext environment;
    @Nullable
    private final WorkflowDefinitionDefaultsContext defaults;
    @Nullable
    private final WorkflowDefinitionConcurrencyContext concurrency;
    private final WorkflowDefinitionJobsContext jobs;

    public WorkflowDefinition(JsonNode _root) {
        if (_root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (!_root.isObject()) throw new IllegalArgumentException("Root should be an object");

        var root = (ObjectNode) _root;

        name = WorkflowDefinitionNameContext.create(root.get("name")).orElse(null);
        runName = WorkflowDefinitionRunNameContext.create(root.get("runName")).orElse(null);
        on = WorkflowDefinitionOnContext.ensure(root.get("on"));
        permissions = WorkflowDefinitionPermissionsContext.create(root.get("permissions"), Set.of()).orElse(null);
        environment = WorkflowDefinitionEnvironmentContext.create(root.get("env")).orElse(null);
        defaults = WorkflowDefinitionDefaultsContext.create(root.get("defaults")).orElse(null);
        concurrency = WorkflowDefinitionConcurrencyContext.create(root.get("concurrency")).orElse(null);
        jobs = WorkflowDefinitionJobsContext.ensure(root.get("jobs"));
    }

    public <T extends SystemEvent> Boolean on(EventWrapper<T> event) {
        Boolean result = on.on(event);
        log.info("[id:{}][type:{}] Event match: {}", event.getId(), event.getType(), result);

        return result;
    }
}
