package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.*;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.on.OnContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import jakarta.annotation.Nullable;
import lombok.Builder;
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
    private final String runName;
    private final OnContext on;
    @Nullable
    private final PermissionsContext permissions;
    @Nullable
    private final EnvironmentContext environment;
    @Nullable
    private final DefaultsContext defaults;
    @Nullable
    private final ConcurrencyContext concurrency;
    private final JobsContext jobs;

    public WorkflowDefinition(JsonNode _root) {
        if (_root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (!_root.isObject()) throw new IllegalArgumentException("Root should be an object");

        var root = (ObjectNode) _root;

        name = NameContext.create(root.get("name")).orElse(null);
        runName = RunNameContext.create(root.get("runName")).orElse(null);
        on = OnContext.ensure(root.get("on"));
        permissions = PermissionsContext.create(root.get("permissions"), Set.of()).orElse(null);
        environment = EnvironmentContext.create(root.get("env")).orElse(null);
        defaults = DefaultsContext.create(root.get("defaults")).orElse(null);
        concurrency = ConcurrencyContext.create(root.get("concurrency")).orElse(null);
        jobs = JobsContext.ensure(root.get("jobs"));
    }

    public <T extends SystemEvent> Boolean on(EventWrapper<T> event) {
        Boolean result = on.on(event);
        log.info("[id:{}][type:{}] Event match: {}", event.getId(), event.getType(), result);

        return result;
    }
}
