package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystemProvider;
import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.projects.domain.event_handlers.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

import static eu.venthe.pipeline.orchestrator.projects.domain.utilities.PipelineUtilities.resolveFromOrchestratorDirectory;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project implements Aggregate<ProjectId>, ProjectCommands {
    private final EventHandlerProvider eventHandlerProvider;
    private final VersionControlSystemProvider versionControlSystemProvider;
    private final WorkflowFactory workflowFactory;

    @EqualsAndHashCode.Include
    private final ProjectId id;

    @Override
    public Collection<DomainEvent> handleEvent(Event event) {
        return eventHandlerProvider.handle(this, event);
    }

    public Optional<eu.venthe.pipeline.orchestrator.projects.domain.workflows.Workflow> getWorkflow(String ref, String workflow) {
        return versionControlSystemProvider.getFile(id.getId(), ref, resolveFromOrchestratorDirectory(workflow), workflowFactory::fromBytes);
    }
}
