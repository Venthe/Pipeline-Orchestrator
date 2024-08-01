package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.steps.StepExecution;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobRun  {
    private final String name;
    private final StepExecution steps;


}
