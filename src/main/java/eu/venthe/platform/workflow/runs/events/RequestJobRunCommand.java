package eu.venthe.platform.workflow.runs.events;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.definition._archive.steps.StepId;
import eu.venthe.platform.workflow.definition.contexts.JobName;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import lombok.RequiredArgsConstructor;

import java.util.List;

public record RequestJobRunCommand(OrganizationName organizationName,
                                   RepositoryName repositoryName,
                                   WorkflowRunId workflowRunId,
                                   JobName jobName,
                                   int runAttempt,
                                   List<Step> steps) implements DomainTrigger {
    @Override
    public String getType() {
        return "request_job";
    }

    @RequiredArgsConstructor
    public static class Step {
        private final StepId id;
    }

    public static final class RunStep extends Step {
        private final String run;

        public RunStep(final StepId id, final String run) {
            super(id);
            this.run = run;
        }
    }
}
