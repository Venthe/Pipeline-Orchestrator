package eu.venthe.pipeline.orchestrator.projects.domain;

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
public class ProjectSourceConfiguration implements Aggregate<ProjectSourceConfigurationId> {
    @Getter
    private final ProjectSourceConfigurationId id;
    @Getter
    private final String sourceType;

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
