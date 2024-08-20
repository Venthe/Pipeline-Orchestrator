package eu.venthe.platform.workflow.runs.jobs;

import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.definition.contexts.JobId;
import eu.venthe.platform.workflow.definition.contexts.jobs.WorkflowDefinitionJobContext;
import eu.venthe.platform.workflow.model.JobRunId;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

@ToString
@Getter
public class JobRun {
    private JobRunAttempt attempt;

    @Getter
    private JobId jobId;
    private WorkflowDefinitionJobContext jobContext;

    JobRun(JobId jobId, WorkflowDefinitionJobContext jobContext) {
        this.jobContext = jobContext;
        this.jobId = jobId;
    }

    JobRunId getJobRunId() {
        return new JobRunId(jobId, attempt.getAttemptNumber());
    }

    RequestJobRunSpecification run() {
        attempt = new JobRunAttempt(1);
        return new RequestJobRunSpecification(getJobId(), attempt.getAttemptNumber(), attempt.getToken());
    }

    List<DomainTrigger> notifyJobStarted(final ZonedDateTime date) {
        return List.of();
    }

    public record RequestJobRunSpecification(JobId jobId, int runAttempt, RunCallbackToken token) {}
}
