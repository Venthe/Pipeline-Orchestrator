package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.JobContext;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

@ToString
public class JobRun {
    private JobRunAttempt attempt;

    private final Pair<JobId, JobContext> jobContext;

    JobRun(final Pair<JobId, JobContext> jobContext) {
        this.jobContext = jobContext;
    }

    JobId getJobId() {
        return jobContext.getKey();
    }

    RequestJobRunSpecification run() {
        attempt = new JobRunAttempt(1);
        return new RequestJobRunSpecification(getJobId(), attempt.getAttemptNumber(), attempt.getToken());
    }

    public record RequestJobRunSpecification(JobId jobId, int runAttempt, RunCallbackToken token) {}
}
