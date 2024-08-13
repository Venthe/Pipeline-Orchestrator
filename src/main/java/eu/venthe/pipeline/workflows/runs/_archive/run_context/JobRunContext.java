package eu.venthe.pipeline.workflows.runs._archive.run_context;

import eu.venthe.pipeline.workflows.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;

public record JobRunContext(JobMetaContext jobMetaContext) {

    public record JobMetaContext(WorkflowDefinitionStepsContext steps) {

    }
}
