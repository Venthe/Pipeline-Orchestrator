package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import lombok.ToString;

@ToString
public class JobRun {
    ProjectId getProjectId() {
        throw new UnsupportedOperationException();
    }

    WorkflowRunId getWorkflowRunId() {
        throw new UnsupportedOperationException();
    }

    JobId getJobId() {
        throw new UnsupportedOperationException();
    }

    void run() {
        throw new UnsupportedOperationException();
    }
}
