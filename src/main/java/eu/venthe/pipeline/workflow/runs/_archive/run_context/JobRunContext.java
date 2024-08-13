package eu.venthe.pipeline.workflow.runs._archive.run_context;

import eu.venthe.pipeline.workflow.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;

public record JobRunContext(JobMetaContext jobMetaContext) {

    public record JobMetaContext(WorkflowDefinitionStepsContext steps) {

    }
}
