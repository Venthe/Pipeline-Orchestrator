package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.JobContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.Runner;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

@ToString
public class JobRun {
    private JobRunAttempt attempt;

    private final WorkflowExecutionCommandService.Context context;
    private final Pair<JobId, JobContext> jobContext;
    @ToString.Exclude
    private final WorkflowRun workflowRun;
    private final TimeService timeService;

    JobRun(final WorkflowExecutionCommandService.Context context,
           final Pair<JobId, JobContext> jobContext,
           final WorkflowRun workflowRun,
           final TimeService timeService) {
        this.context = context;
        this.jobContext = jobContext;
        this.workflowRun = workflowRun;
        this.timeService = timeService;
    }

    ProjectId getProjectId() {
        return context.id();
    }

    WorkflowRunId getWorkflowRunId() {
        return workflowRun.getId();
    }

    JobId getJobId() {
        return jobContext.getKey();
    }

    void run(Runner runner) {
        attempt = new JobRunAttempt(1, this);
        attempt.request(runner);
    }
}
