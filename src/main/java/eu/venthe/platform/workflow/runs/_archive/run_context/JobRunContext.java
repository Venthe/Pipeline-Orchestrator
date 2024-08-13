package eu.venthe.platform.workflow.runs._archive.run_context;

import eu.venthe.platform.workflow.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;

public record JobRunContext(JobMetaContext jobMetaContext) {

    public record JobMetaContext(WorkflowDefinitionStepsContext steps) {

    }
}
