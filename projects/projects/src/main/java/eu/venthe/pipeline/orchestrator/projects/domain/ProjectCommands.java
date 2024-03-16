package eu.venthe.pipeline.orchestrator.projects.domain;

import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;

import java.util.Set;

public interface ProjectCommands {
    default Set<DomainEvent> handleEvent(ObjectNode event) {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> registerManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> unregisterManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> refreshProject() {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> registerTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> unregisterTrackedRef(String ref) {
        throw new UnsupportedOperationException();
    }

    default Set<DomainEvent> executeManualWorkflow(String path) {
        throw new UnsupportedOperationException();
    }
}
