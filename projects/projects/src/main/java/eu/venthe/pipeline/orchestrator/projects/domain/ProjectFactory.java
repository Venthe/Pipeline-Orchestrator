package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.configuration.domain.ProjectSourceConfigurationRepository;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ProjectFactory {
    private final ProjectSourceConfigurationRepository projectSourceConfigurationRepository;

    public Pair<Project, Collection<DomainEvent>> createProject() {
        throw new UnsupportedOperationException();
    }
}
