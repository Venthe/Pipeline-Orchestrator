package eu.venthe.pipeline.orchestrator.projects.domain.workflows;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.events.EventWrapper;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.*;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.on.OnContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
@Getter
public class Workflow {

    private final ObjectNode root;
    private final WorkflowRef ref;
    private final OnContext onContext;
    private JobsContext jobsContext;

    public Workflow(ObjectNode root, WorkflowRef workflowRef) {
        if (root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (workflowRef == null) throw new IllegalArgumentException("WorkflowRef should not be null");

        this.root = root;
        this.ref = workflowRef;

        onContext = OnContext.ensure(root.get("on"));
        jobsContext = JobsContext.ensure(root.get("jobs"));
    }

     /*
     * The name of the workflow. If you omit name, System displays the workflow file path relative to the root of the repository.
    */

    public String getName() {
        return NameContext.name(root).orElse(ref .getFilePath());
    }

    public Optional<String> getRunName() {
        return RunNameContext.runName(root);
    }

    public Optional<PermissionsContext> getPermissions() {
        return PermissionsContext.create(root);
    }

    public Optional<EnvironmentContext> getEnv() {
        return EnvironmentContext.create(root);
    }

    public Optional<DefaultsContext> getDefaults() {
        return DefaultsContext.create(root);
    }

    public Optional<ConcurrencyContext> getConcurrency() {
        return ConcurrencyContext.create(root);
    }

    public JobsContext getJobs() {
        return jobsContext;
    }

    public <T extends Event> Boolean on(EventWrapper<T> event) {

        Boolean result = getOnContext().on(event);
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
