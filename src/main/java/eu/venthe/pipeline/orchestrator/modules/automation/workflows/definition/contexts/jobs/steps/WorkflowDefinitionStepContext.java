package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.steps;

public class WorkflowDefinitionStepContext {
    jobs.<job_id>.steps[*].id
    jobs.<job_id>.steps[*].if
    jobs.<job_id>.steps[*].name
    jobs.<job_id>.steps[*].uses
    jobs.<job_id>.steps[*].run
    jobs.<job_id>.steps[*].working-directory
    jobs.<job_id>.steps[*].shell
    jobs.<job_id>.steps[*].with
    jobs.<job_id>.steps[*].with.args
    jobs.<job_id>.steps[*].with.entrypoint
    jobs.<job_id>.steps[*].env
    jobs.<job_id>.steps[*].continue-on-error
    jobs.<job_id>.steps[*].timeout-minutes
}
