package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.ProjectStatus;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.ProjectEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static eu.venthe.pipeline.orchestrator.projects.domain.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project implements Aggregate<ProjectId> {

    @EqualsAndHashCode.Include
    private final ProjectId id;

    private ProjectStatus status;

    public Function<EventHandlerProvider, Collection<DomainEvent>> handleEvent(ProjectEvent event) {
        return eventHandlerProvider -> eventHandlerProvider.handle(this, event);
    }

    public BiFunction<VersionControlSystemProvider, WorkflowFactory, Optional<eu.venthe.pipeline.orchestrator.projects.domain.workflows.Workflow>> getWorkflow(String ref, String workflow) {
        return (versionControlSystemProvider, workflowFactory) -> versionControlSystemProvider.getFile(id.getId(), ref, resolveFromOrchestratorDirectory(workflow), workflowFactory::fromBytes);
    }

    public Collection<DomainEvent> registerManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> unregisterManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> refreshProject() {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> executeManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }
}
