package eu.venthe.pipeline.orchestrator.events.contexts.definitions;

@Deprecated
public interface InstallationLite {
    /**
     * The ID of the installation.
     */
    default Integer getId() {
        throw new UnsupportedOperationException();
    }

    default String getNodeId() {
        throw new UnsupportedOperationException();
    }
}
