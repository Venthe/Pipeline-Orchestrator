package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;

public class GithubContext {
    private final String action;
    private final String actionPath;
    private final String actionRef;
    private final String actionRepository;
    private final String actionStatus;
    private final String actor;
    private final String actorId;
    private final String apiUrl;
    private final String baseRef;
    private final String env;
    private final ObjectNode event;
    private final String eventName;
    private final String eventPath;
    private final String graphqlUrl;
    private final String headRef;
    private final String job;
    private final String path;
    private final String ref;
    private final String refName;
    private final Boolean refProtected;
    private final String refType;
    private final String repository;
    private final String repositoryId;
    private final String repositoryOwner;
    private final String repositoryOwnerId;
    private final String repositoryUrl;
    private final String retentionDays;
    private final String runId;
    private final String runNumber;
    private final String runAttempt;
    private final String secretSource;
    private final String serverUrl;
    private final String sha;
    private final String token;
    private final String triggeringActor;
    private final String workflow;
    private final String workflowRef;
    private final String workflowSha;
    private final String workspace;

    public GithubContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        action = ContextUtilities.Text.ensure(root.get("action"));
        actionPath = ContextUtilities.Text.ensure(root.get("actionPath"));
        actionRef = ContextUtilities.Text.ensure(root.get("actionRef"));
        actionRepository = ContextUtilities.Text.ensure(root.get("actionRepository"));
        actionStatus = ContextUtilities.Text.ensure(root.get("actionStatus"));
        actor = ContextUtilities.Text.ensure(root.get("actor"));
        actorId = ContextUtilities.Text.ensure(root.get("actorId"));
        apiUrl = ContextUtilities.Text.ensure(root.get("apiUrl"));
        baseRef = ContextUtilities.Text.ensure(root.get("baseRef"));
        env = ContextUtilities.Text.ensure(root.get("env"));
        event = ContextUtilities.validateIsObjectNode(root.get("event"));
        eventName = ContextUtilities.Text.ensure(root.get("eventName"));
        eventPath = ContextUtilities.Text.ensure(root.get("eventPath"));
        graphqlUrl = ContextUtilities.Text.ensure(root.get("graphqlUrl"));
        headRef = ContextUtilities.Text.ensure(root.get("headRef"));
        job = ContextUtilities.Text.ensure(root.get("job"));
        path = ContextUtilities.Text.ensure(root.get("path"));
        ref = ContextUtilities.Text.ensure(root.get("ref"));
        refName = ContextUtilities.Text.ensure(root.get("refName"));
        refProtected = ContextUtilities.ensure(root.get("refProtected"), ContextUtilities.toBoolean());
        refType = ContextUtilities.Text.ensure(root.get("refType"));
        repository = ContextUtilities.Text.ensure(root.get("repository"));
        repositoryId = ContextUtilities.Text.ensure(root.get("repositoryId"));
        repositoryOwner = ContextUtilities.Text.ensure(root.get("repositoryOwner"));
        repositoryOwnerId = ContextUtilities.Text.ensure(root.get("repositoryOwnerId"));
        repositoryUrl = ContextUtilities.Text.ensure(root.get("repositoryUrl"));
        retentionDays = ContextUtilities.Text.ensure(root.get("retentionDays"));
        runId = ContextUtilities.Text.ensure(root.get("runId"));
        runNumber = ContextUtilities.Text.ensure(root.get("runNumber"));
        runAttempt = ContextUtilities.Text.ensure(root.get("runAttempt"));
        secretSource = ContextUtilities.Text.ensure(root.get("secretSource"));
        serverUrl = ContextUtilities.Text.ensure(root.get("serverUrl"));
        sha = ContextUtilities.Text.ensure(root.get("sha"));
        token = ContextUtilities.Text.ensure(root.get("token"));
        triggeringActor = ContextUtilities.Text.ensure(root.get("triggeringActor"));
        workflow = ContextUtilities.Text.ensure(root.get("workflow"));
        workflowRef = ContextUtilities.Text.ensure(root.get("workflowRef"));
        workflowSha = ContextUtilities.Text.ensure(root.get("workflowSha"));
        workspace = ContextUtilities.Text.ensure(root.get("workspace"));
    }

    public static GithubContext ensure(JsonNode github) {
        throw new UnsupportedOperationException();
    }
}
