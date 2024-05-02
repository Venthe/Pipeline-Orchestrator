package eu.venthe.pipeline.orchestrator.projects.domain.workflows;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.*;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.shared_context.ConcurrencyContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.shared_context.DefaultsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.shared_context.EnvironmentContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.shared_context.PermissionsContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Optional;

@SuppressWarnings("ALL")
@Slf4j
@Getter
public class Workflow {
    @Getter(AccessLevel.NONE)
    private final ObjectNode root;
    private final WorkflowRef ref;

    /*
     * The name of the workflow. If you omit name, System displays the workflow file path relative to the root of the repository.
     */
    private final String name;
    private final Optional<String> runName;
    private final OnContext on;
    private final Optional<PermissionsContext> permissions;
    private final Optional<EnvironmentContext> environment;
    private final Optional<DefaultsContext> defaults;
    private final Optional<ConcurrencyContext> concurrency;
    private final JobsContext jobs;

    public Workflow(JsonNode _root, WorkflowRef workflowRef) {
        if (_root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (workflowRef == null) throw new IllegalArgumentException("WorkflowRef should not be null");
        if (!_root.isObject()) throw new IllegalArgumentException("Root should be an object");

        this.root = (ObjectNode) _root;
        this.ref = workflowRef;

        name = NameContext.create(root.get("name")).orElse(ref.getFilePath());
        runName = RunNameContext.create(root.get("runName"));
        on = OnContext.ensure(root.get("on"));
        permissions = PermissionsContext.create(root.get("permissions"));
        environment = EnvironmentContext.create(root.get("env"));
        defaults = DefaultsContext.create(root.get("defaults"));
        concurrency = ConcurrencyContext.create(root.get("concurrency"));
        jobs = JobsContext.ensure(root.get("jobs"));
    }

    public <T extends ProjectEvent> Boolean on(EventWrapper<T> event) {
        Boolean result = on.on(event);
        log.info("[id:{}][type:{}] Event match: {}", event.getId(), event.getType(), result);

        return result;
    }

    @RequiredArgsConstructor
    @Getter
    public static class WorkflowRef {
        @NonNull
        private final String repositoryName;
        @NonNull
        private final String ref;
        @NonNull
        private final String filePath;
        @NonNull
        private final String sha;

        public String toRef() {
            return MessageFormat.format("{0}@{1}:{2}", repositoryName, ref, filePath);
        }
    }
}
