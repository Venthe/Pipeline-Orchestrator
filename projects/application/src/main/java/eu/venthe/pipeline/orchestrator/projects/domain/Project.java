package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;

public class Project implements Aggregate<ProjectId> {
    @Override
    public ProjectId getId() {
        throw new UnsupportedOperationException();
    }
}
