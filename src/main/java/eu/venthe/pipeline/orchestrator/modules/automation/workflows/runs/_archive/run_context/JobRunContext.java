package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive.run_context;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.steps.WorkflowDefinitionStepsContext;

public record JobRunContext(JobMetaContext jobMetaContext) {

    public record JobMetaContext(WorkflowDefinitionStepsContext steps) {

    }
}
