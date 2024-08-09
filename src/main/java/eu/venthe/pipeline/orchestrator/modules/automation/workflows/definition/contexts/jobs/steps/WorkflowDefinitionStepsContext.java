package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.steps;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WorkflowDefinitionStepsContext {
    private final List<WorkflowDefinitionStepContext> steps;
}
