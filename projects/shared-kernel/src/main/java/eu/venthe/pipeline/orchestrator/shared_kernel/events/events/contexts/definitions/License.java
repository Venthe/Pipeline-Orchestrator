package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions;

import java.net.URI;

public interface License {
    default String getKey() {
        throw new UnsupportedOperationException();
    }

    default String getName() {
        throw new UnsupportedOperationException();
    }

    default String getSpdxId() {
        throw new UnsupportedOperationException();
    }

    default URI getUrl() {
        throw new UnsupportedOperationException();
    }

    default String getNodeId() {
        throw new UnsupportedOperationException();
    }
}
