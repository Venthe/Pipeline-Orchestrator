package eu.venthe.pipeline.orchestrator.workflows;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.events.HandledEvent;
import eu.venthe.pipeline.orchestrator.workflows.contexts.*;
import eu.venthe.pipeline.orchestrator.workflows.contexts.on.OnContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Optional;

@Slf4j
@Getter
public class Workflow {

    private final ObjectNode root;
    private final String sha;
    private final WorkflowRef ref;
    private final OnContext onContext;
    private JobsContext jobsContext;

    public Workflow(ObjectNode root, WorkflowRef workflowRef, String sha) {
        if (root == null) throw new IllegalArgumentException("Workflow should not be null");
        if (sha == null) throw new IllegalArgumentException("Sha should not be null");
        if (workflowRef == null) throw new IllegalArgumentException("WorkflowRef should not be null");

        this.root = root;
        this.ref = workflowRef;
        this.sha = sha;

        onContext = OnContext.create(root)
                .orElseThrow(() -> new RuntimeException("There is no \"on\" property, this workflow will never run"));
        jobsContext = JobsContext.create(root).orElseThrow();
    }

    /**
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

    public Boolean on(HandledEvent event) {

        Boolean result = getOnContext().on(event);
        log.info("[id:{}][type:{}] Event match: {}", event.getId(), event.getType(), result);

        return result;
    }

    @RequiredArgsConstructor
    @Getter
    public static class WorkflowRef {
        private final String repositoryName;
        private final String ref;
        private final String filePath;

        public String toRef() {
            return MessageFormat.format("{0}@{1}:{2}", repositoryName, ref, filePath);
        }
    }
}
