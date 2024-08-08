package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.contexts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

/**
 * The github context contains information about the workflow run and the event that triggered the run. You can also
 * read most of the github context data in environment variables. For more information about environment variables, see
 * "Variables."
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
@ToString
@EqualsAndHashCode
public class GithubContext {
    /**
     * The name of the action currently running, or the id of a step. GitHub removes special characters, and uses the
     * name __run when the current step runs a script without an id. If you use the same action more than once in the
     * same job, the name will include a suffix with the sequence number with underscore before it. For example, the
     * first script you run will have the name __run, and the second script will be named __run_2. Similarly, the second
     * invocation of actions/checkout will be actionscheckout2.
     */
    private final String action;
    /**
     * The path where an action is located. This property is only supported in composite actions. You can use this path
     * to access files located in the same repository as the action, for example by changing directories to the path: cd
     * ${{ github.action_path }} .
     */
    private final Optional<String> actionPath;
    /**
     * For a step executing an action, this is the ref of the action being executed. For example, v2.
     * <p>
     * Do not use in the run keyword. To make this context work with composite actions, reference it within the env
     * context of the composite action.
     */
    private final String actionRef;
    /**
     * For a step executing an action, this is the owner and repository name of the action. For example,
     * actions/checkout.
     * <p>
     * Do not use in the run keyword. To make this context work with composite actions, reference it within the env
     * context of the composite action.
     */
    private final String actionRepository;
    /**
     * For a composite action, the current result of the composite action.
     */
    private final Optional<String> actionStatus;
    /**
     * The username of the user that triggered the initial workflow run. If the workflow run is a re-run, this value may
     * differ from github.triggering_actor. Any workflow re-runs will use the privileges of github.actor, even if the
     * actor initiating the re-run (github.triggering_actor) has different privileges.
     */
    private final String actor;
    /**
     * The account ID of the person or app that triggered the initial workflow run. For example, 1234567. Note that this
     * is different from the actor username.
     */
    private final Optional<String> actorId;
    /**
     * The URL of the GitHub REST API.
     */
    private final String apiUrl;
    /**
     * The base_ref or target branch of the pull request in a workflow run. This property is only available when the
     * event that triggers a workflow run is either pull_request or pull_request_target.
     */
    private final String baseRef;
    /**
     * Path on the runner to the file that sets environment variables from workflow commands. This file is unique to the
     * current step and is a different file for each step in a job. For more information, see "Workflow commands for
     * GitHub Actions."
     */
    private final String env;
    /**
     * The full event webhook payload. You can access individual properties of the event using this context. This object
     * is identical to the webhook payload of the event that triggered the workflow run, and is different for each
     * event. The webhooks for each GitHub Actions event is linked in "Events that trigger workflows." For example, for
     * a workflow run triggered by the push event, this object contains the contents of the push webhook payload.
     */
    private final ObjectNode event;
    /**
     * The name of the event that triggered the workflow run.
     */
    private final String eventName;
    /**
     * The path to the file on the runner that contains the full event webhook payload.
     */
    private final String eventPath;
    /**
     * The URL of the GitHub GraphQL API.
     */
    private final String graphqlUrl;
    /**
     * The head_ref or source branch of the pull request in a workflow run. This property is only available when the
     * event that triggers a workflow run is either pull_request or pull_request_target.
     */
    private final String headRef;
    /**
     * The job_id of the current job. Note: This context property is set by the Actions runner, and is only available
     * within the execution steps of a job. Otherwise, the value of this property will be null.
     */
    private final String job;
    /**
     * Path on the runner to the file that sets system PATH variables from workflow commands. This file is unique to the
     * current step and is a different file for each step in a job. For more information, see "Workflow commands for
     * GitHub Actions."
     */
    private final String path;
    /**
     * The fully-formed ref of the branch or tag that triggered the workflow run. For workflows triggered by push, this
     * is the branch or tag ref that was pushed. For workflows triggered by pull_request, this is the pull request merge
     * branch. For workflows triggered by release, this is the release tag created. For other triggers, this is the
     * branch or tag ref that triggered the workflow run. This is only set if a branch or tag is available for the event
     * type. The ref given is fully-formed, meaning that for branches the format is refs/heads/<branch_name>, for pull
     * requests it is refs/pull/<pr_number>/merge, and for tags it is refs/tags/<tag_name>. For example,
     * refs/heads/feature-branch-1.
     */
    private final String ref;
    /**
     * The short ref name of the branch or tag that triggered the workflow run. This value matches the branch or tag
     * name shown on GitHub. For example, feature-branch-1.
     * <p>
     * For pull requests, the format is <pr_number>/merge.
     */
    private final String refName;
    /**
     * true if branch protections or rulesets are configured for the ref that triggered the workflow run.
     */
    private final Boolean refProtected;
    /**
     * The type of ref that triggered the workflow run. Valid values are branch or tag.
     */
    private final String refType;
    /**
     * The owner and repository name. For example, octocat/Hello-World.
     */
    private final String repository;
    /**
     * The ID of the repository. For example, 123456789. Note that this is different from the repository name.
     */
    private final Optional<String> repositoryId;
    /**
     * The repository owner's username. For example, octocat.
     */
    private final String repositoryOwner;
    /**
     * The repository owner's account ID. For example, 1234567. Note that this is different from the owner's name.
     */
    private final Optional<String> repositoryOwnerId;
    /**
     * The Git URL to the repository. For example, git://github.com/octocat/hello-world.git.
     */
    private final String repositoryUrl;
    /**
     * The number of days that workflow run logs and artifacts are kept.
     */
    private final String retentionDays;
    /**
     * A unique number for each workflow run within a repository. This number does not change if you re-run the workflow
     * run.
     */
    private final String runId;
    /**
     * A unique number for each run of a particular workflow in a repository. This number begins at 1 for the workflow's
     * first run, and increments with each new run. This number does not change if you re-run the workflow run.
     */
    private final String runNumber;
    /**
     * A unique number for each attempt of a particular workflow run in a repository. This number begins at 1 for the
     * workflow run's first attempt, and increments with each re-run.
     */
    private final String runAttempt;
    /**
     * The source of a secret used in a workflow. Possible values are None, Actions, Codespaces, or Dependabot.
     */
    private final String secretSource;
    /**
     * The URL of the GitHub server. For example: https://github.com.
     */
    private final String serverUrl;
    /**
     * The commit SHA that triggered the workflow. The value of this commit SHA depends on the event that triggered the
     * workflow. For more information, see "Events that trigger workflows." For example,
     * ffac537e6cbbf934b08745a378932722df287a53.
     */
    private final String sha;
    /**
     * A token to authenticate on behalf of the GitHub App installed on your repository. This is functionally equivalent
     * to the GITHUB_TOKEN secret. For more information, see "Automatic token authentication." Note: This context
     * property is set by the Actions runner, and is only available within the execution steps of a job. Otherwise, the
     * value of this property will be null.
     */
    private final String token;
    /**
     * The username of the user that initiated the workflow run. If the workflow run is a re-run, this value may differ
     * from github.actor. Any workflow re-runs will use the privileges of github.actor, even if the actor initiating the
     * re-run (github.triggering_actor) has different privileges.
     */
    private final Optional<String> triggeringActor;
    /**
     * The name of the workflow. If the workflow file doesn't specify a name, the value of this property is the full
     * path of the workflow file in the repository.
     */
    private final String workflow;
    /**
     * The ref path to the workflow. For example,
     * octocat/hello-world/.github/workflows/my-workflow.yml@refs/heads/my_branch.
     */
    private final Optional<String> workflowRef;
    /**
     * The commit SHA for the workflow file.
     */
    private final Optional<String> workflowSha;
    /**
     * The default working directory on the runner for steps, and the default location of your repository when using the
     * checkout action.
     */
    private final String workspace;

    @JsonCreator
    public GithubContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        action = ContextUtilities.Text.ensure(root.get("action"), () -> new IllegalArgumentException("Missing github.action"));
        actionPath = ContextUtilities.Text.create(root.get("action_path"));
        actionRef = ContextUtilities.Text.ensure(root.get("action_ref"), () -> new IllegalArgumentException("Missing github.action_ref"));
        actionRepository = ContextUtilities.Text.ensure(root.get("action_repository"), () -> new IllegalArgumentException("Missing github.action_repository"));
        actionStatus = ContextUtilities.Text.create(root.get("action_status"));
        actor = ContextUtilities.Text.ensure(root.get("actor"), () -> new IllegalArgumentException("Missing github.actor"));
        actorId = ContextUtilities.Text.create(root.get("actor_id"));
        apiUrl = ContextUtilities.Text.ensure(root.get("api_url"), () -> new IllegalArgumentException("Missing github.api_url"));
        baseRef = ContextUtilities.Text.ensure(root.get("base_ref"), () -> new IllegalArgumentException("Missing github.base_ref"));
        env = ContextUtilities.Text.ensure(root.get("env"), () -> new IllegalArgumentException("Missing github.env"));
        event = ContextUtilities.validateIsObjectNode(root.get("event"));
        eventName = ContextUtilities.Text.ensure(root.get("event_name"), () -> new IllegalArgumentException("Missing github.event_name"));
        eventPath = ContextUtilities.Text.ensure(root.get("event_path"), () -> new IllegalArgumentException("Missing github.event_path"));
        graphqlUrl = ContextUtilities.Text.ensure(root.get("graphql_url"), () -> new IllegalArgumentException("Missing github.graphql_url"));
        headRef = ContextUtilities.Text.ensure(root.get("head_ref"), () -> new IllegalArgumentException("Missing github.head_ref"));
        job = ContextUtilities.Text.ensure(root.get("job"), () -> new IllegalArgumentException("Missing github.job"));
        path = ContextUtilities.Text.ensure(root.get("path"), () -> new IllegalArgumentException("Missing github.path"));
        ref = ContextUtilities.Text.ensure(root.get("ref"), () -> new IllegalArgumentException("Missing github.ref"));
        refName = ContextUtilities.Text.ensure(root.get("ref_name"), () -> new IllegalArgumentException("Missing github.ref_name"));
        refProtected = ContextUtilities.ensure(root.get("ref_protected"), ContextUtilities.toBoolean(), () -> new IllegalArgumentException("Missing github.ref_protected"));
        refType = ContextUtilities.Text.ensure(root.get("ref_type"), () -> new IllegalArgumentException("Missing github.ref_type"));
        repository = ContextUtilities.Text.ensure(root.get("repository"), () -> new IllegalArgumentException("Missing github.repository"));
        repositoryId = ContextUtilities.Text.create(root.get("repository_id"));
        repositoryOwner = ContextUtilities.Text.ensure(root.get("repository_owner"), () -> new IllegalArgumentException("Missing github.repository_owner"));
        repositoryOwnerId = ContextUtilities.Text.create(root.get("repository_owner_id"));
        repositoryUrl = ContextUtilities.Text.ensure(root.get("repository_url"), () -> new IllegalArgumentException("Missing github.repository_url"));
        retentionDays = ContextUtilities.Text.ensure(root.get("retention_days"), () -> new IllegalArgumentException("Missing github.retention_days"));
        runId = ContextUtilities.Text.ensure(root.get("run_id"), () -> new IllegalArgumentException("Missing github.run_id"));
        runNumber = ContextUtilities.Text.ensure(root.get("run_number"), () -> new IllegalArgumentException("Missing github.run_number"));
        runAttempt = ContextUtilities.Text.ensure(root.get("run_attempt"), () -> new IllegalArgumentException("Missing github.run_attempt"));
        secretSource = ContextUtilities.Text.ensure(root.get("secret_source"), () -> new IllegalArgumentException("Missing github.secret_source"));
        serverUrl = ContextUtilities.Text.ensure(root.get("server_url"), () -> new IllegalArgumentException("Missing github.server_url"));
        sha = ContextUtilities.Text.ensure(root.get("sha"), () -> new IllegalArgumentException("Missing github.sha"));
        token = ContextUtilities.Text.ensure(root.get("token"), () -> new IllegalArgumentException("Missing github.token"));
        triggeringActor = ContextUtilities.Text.create(root.get("triggering_actor"));
        workflow = ContextUtilities.Text.ensure(root.get("workflow"), () -> new IllegalArgumentException("Missing github.workflow"));
        workflowRef = ContextUtilities.Text.create(root.get("workflow_ref"));
        workflowSha = ContextUtilities.Text.create(root.get("workflow_sha"));
        workspace = ContextUtilities.Text.ensure(root.get("workspace"), () -> new IllegalArgumentException("Missing github.workspace"));
    }

    public static GithubContext ensure(JsonNode github) {
        return ContextUtilities.ensure(github, GithubContext::new, () -> new IllegalArgumentException("Missing github"));
    }
}
