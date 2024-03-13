package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectSourceConfigurationFactory {

    private final Set<eu.venthe.pipeline.orchestrator.plugins.projects.ProjectSourceConfigurationFactory> factories;

    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> create(ProjectSourceConfigurationDto configurationDto) {
    }
}
