package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;
import eu.venthe.pipeline.orchestrator.plugins.projects.VersionControlSystem;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@RequiredArgsConstructor
@Getter
public class ProjectSourceConfiguration implements Aggregate<ProjectSourceConfigurationId> {
    private final ProjectSourceConfigurationId id;
    private final String sourceType;
    private final ProjectProvider projectProvider;
    private final VersionControlSystem versionControlSystem;

    public Collection<DomainEvent> synchronize() {
        throw new UnsupportedOperationException();
    }

    public Collection<DomainEvent> delete() {
        throw new UnsupportedOperationException();
    }

    public <T> T visitor(ProjectSourceVisitor<T> visitor) {
        return visitor.visit();
    }
}
