package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.api.Event;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;

import java.util.Collection;
import java.util.Set;

public interface ProjectCommands {
    default Collection<DomainEvent> handleEvent(Event event) {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> registerManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> unregisterManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> refreshProject() {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    default Collection<DomainEvent> executeManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }
}
