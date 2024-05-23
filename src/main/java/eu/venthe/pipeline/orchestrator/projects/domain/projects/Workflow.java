package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.workflows.WorkflowDefinitionFactory;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.ProjectDataProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
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

    public Collection<DomainEvent> registerManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> unregisterManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> executeManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }
}
