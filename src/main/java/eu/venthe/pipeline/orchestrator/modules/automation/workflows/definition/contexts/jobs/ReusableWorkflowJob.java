package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.PermissionsContext;

import java.util.Optional;

public class ReusableWorkflowJob {
    private final Optional<UsesContext> uses;
    private final Optional<WithContext> with;
    private final Optional<SecretsContext> secrets;
    private final Optional<PermissionsContext> permissions;
}
