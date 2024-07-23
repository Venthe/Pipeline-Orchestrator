package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution._archive;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.steps.StepExecution;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobRun  {
    private final String name;
    private final StepExecution steps;


}
