package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.steps;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@RequiredArgsConstructor
public class StepDefinition {
    Optional<StepId> id;
}
