package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application.ProjectSourceConfigurationDto;
import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectSourceConfigurationFactory {
    private final FeatureManager featureManager;

    public Pair<ProjectSourceConfiguration, Collection<DomainEvent>> create(ProjectSourceConfigurationDto configurationDto) {
        if (featureManager.isActive(ProjectsSourceFeatureFlags.PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP.getFeature())) {
            ProjectSourceConfigurationId id = new ProjectSourceConfigurationId(configurationDto.getName());
            return Pair.of(new ProjectSourceConfiguration(id), Set.of(new ProjectSourceConfigurationAddedEvent(id.value())));
        }

        throw new UnsupportedOperationException();
    }
}
