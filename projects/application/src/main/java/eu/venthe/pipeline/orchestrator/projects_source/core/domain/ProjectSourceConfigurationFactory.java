package eu.venthe.pipeline.orchestrator.projects_source.core.domain;

import eu.venthe.pipeline.orchestrator.projects_source.core.application.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Collection;
import java.util.Set;

@Service
public class ProjectSourceConfigurationFactory {
    private final FeatureManager featureManager;

    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> create(ProjectSourceConfigurationDto configurationDto) {
        if (featureManager.isActive(ProjectsSourceFeatureFlags.PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP.getFeature())) {
            return Pair.of(null, Set.of(new ProjectSourceConfigurationAddedEvent()));
        }

        throw new UnsupportedOperationException();
    }
}
