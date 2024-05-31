package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.WorkflowDefinitionFactory;
import eu.venthe.pipeline.orchestrator.projects.domain.plugin_template.ProjectDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;

import static eu.venthe.pipeline.orchestrator.projects.domain.projects.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
class Workflow {

    private final Project project;

    public BiFunction<ProjectDataProvider, WorkflowDefinitionFactory, Optional<WorkflowDefinition>> getWorkflow(String ref, String workflow) {
        return (versionControlSystemProvider, workflowDefinitionFactory) -> versionControlSystemProvider.getFile(project.getId().serialize(), ref, resolveFromOrchestratorDirectory(workflow)).map(WorkflowDefinitionFactory::fromBytes);
    }

    public Collection<DomainTrigger> registerManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainTrigger> unregisterManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainTrigger> executeManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }
}
