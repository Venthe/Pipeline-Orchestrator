package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.on.OnContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.NameContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.RunNameContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.FileHash;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.ConcurrencyContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.DefaultsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.EnvironmentContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.PermissionsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("ALL")
@Slf4j
@Getter
@ToString(onlyExplicitlyIncluded = true)
public class WorkflowDefinition {
    @Getter(AccessLevel.NONE)
    @ToString.Include
    private final ObjectNode root;

    /*
     * The name of the workflow. If you omit name, System displays the workflow file path relative to the root of the repository.
     */
    private final Optional<String> name;
    private final Optional<String> runName;
    private final OnContext on;
    private final Optional<PermissionsContext> permissions;
    private final Optional<EnvironmentContext> environment;
    private final Optional<DefaultsContext> defaults;
    private final Optional<ConcurrencyContext> concurrency;
    private final JobsContext jobs;

    public WorkflowDefinition(JsonNode _root) {
        if (_root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (!_root.isObject()) throw new IllegalArgumentException("Root should be an object");

        this.root = (ObjectNode) _root;

        name = NameContext.create(root.get("name"));
        runName = RunNameContext.create(root.get("runName"));
        on = OnContext.ensure(root.get("on"));
        permissions = PermissionsContext.create(root.get("permissions"), Set.of());
        environment = EnvironmentContext.create(root.get("env"));
        defaults = DefaultsContext.create(root.get("defaults"));
        concurrency = ConcurrencyContext.create(root.get("concurrency"));
        jobs = JobsContext.ensure(root.get("jobs"));
    }

    public <T extends SystemEvent> Boolean on(EventWrapper<T> event) {
        Boolean result = on.on(event);
        log.info("[id:{}][type:{}] Event match: {}", event.getId(), event.getType(), result);

        return result;
    }
}
