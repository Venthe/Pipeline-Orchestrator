package eu.venthe.platform.runner.runner_engine.template.model;

import eu.venthe.platform.workflow.definition.contexts.JobId;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.shared_kernel.project.ProjectId;
import eu.venthe.platform.shared_kernel.dynamic_variable.DynamicProperty;
import eu.venthe.platform.shared_kernel.git.GitRevision;
import eu.venthe.platform.shared_kernel.system_events.SystemEvent;
import jakarta.annotation.Nullable;

import java.util.Map;

public record JobRunRequestContext(
        SystemContext system,
        Map<String, String> initialEnvironmentVariables,
        Map<String, String> initialConstantVariables,
        Map<JobId, PreviousJobContext> jobs,
        Map<JobId, PreviousJobContext> needs,
        Strategy strategy,
        @Nullable Matrix matrix,
        Map<String, DynamicProperty> inputs,
        Map<String, Service> services
) {

    /**
     * The type Run context.
     *
     * @param actor              user that triggered the initial workflow run.
     * @param baseRevision       target branch of the pull request in a workflow run.
     * @param headRevision       source branch of the pull request in a workflow run.
     * @param revision           The fully-formed ref of the branch or tag that triggered the workflow run. For
     *                           workflows triggered by push, this is the branch or tag ref that was pushed. For
     *                           workflows triggered by pull_request, this is the pull request merge branch. For
     *                           workflows triggered by release, this is the release tag created. For other triggers,
     *                           this is the branch or tag ref that triggered the workflow run. This is only set if a
     *                           branch or tag is available for the event type. The ref given is fully-formed, meaning
     *                           that for branches the format is refs/heads/<branch_name>, for pull requests it is
     *                           refs/pull/<pr_number>/merge, and for tags it is refs/tags/<tag_name>. For example,
     *                           refs/heads/feature-branch-1.
     * @param event              The full event webhook payload.
     * @param jobId              The job_id of the current job.
     * @param projectId
     * @param triggeringActor
     * @param repositoryOwner
     * @param retentionDays
     * @param workflowRunId
     * @param workflowRunAttempt
     * @param callbackToken
     * @param workflowName
     * @param workflowRevision
     */
    public record SystemContext(
            UserContext actor,
            @Nullable GitRevision baseRevision,
            @Nullable GitRevision headRevision,
            @Nullable GitRevision revision,
            SystemEvent event,
            JobRunId jobRunId,
            ProjectId projectId,
            UserContext triggeringActor,
            UserContext repositoryOwner,
            Integer retentionDays,
            WorkflowRunId workflowRunId,
            Integer workflowRunAttempt,
            RunCallbackToken callbackToken,
            String workflowName,
            GitRevision workflowRevision) {
    }

    public record Service(String image, Map<Integer, Integer> ports, Map<String, String> options) {
    }

    /**
     * jobs.<job_id>.result	string	The result of a job in the reusable workflow. Possible values are success, failure,
     * cancelled, or skipped. jobs.<job_id>.outputs	object	The set of outputs of a job in a reusable workflow.
     * jobs.<job_id>.outputs.<output_name>	string	The value of a specific output for a job in a reusable workflow.
     */
    public record PreviousJobContext() {
    }

    /**
     * strategy.fail-fast	boolean	When this evaluates to true, all in-progress jobs are canceled if any job in a matrix fails. For more information, see "Workflow syntax for GitHub Actions."
     * strategy.job-index	number	The index of the current job in the matrix. Note: This number is a zero-based number. The first job's index in the matrix is 0.
     * strategy.job-total	number	The total number of jobs in the matrix. Note: This number is not a zero-based number. For example, for a matrix with four jobs, the value of job-total is 4.
     * strategy.max-parallel	number	The maximum number of jobs that can run simultaneously when using a matrix job strategy. For more information, see "Workflow syntax for GitHub Actions."
     */
    public record Strategy() {

    }

    public record Matrix() {}

    public record UserContext() {
    }
}
