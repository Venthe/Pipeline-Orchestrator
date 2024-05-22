package eu.venthe.pipeline.orchestrator.projects._projects.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ProjectFactory {
    public Pair<Project, Collection<DomainEvent>> createProject() {
        throw new UnsupportedOperationException();
    }
}
