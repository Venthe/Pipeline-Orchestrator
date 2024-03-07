package eu.venthe.pipeline.orchestrator.projects_source.core.application;

import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfigurationFactory;
import eu.venthe.pipeline.orchestrator.projects_source.core.domain.ProjectSourceConfigurationId;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectsSourceConfigurationServiceImpl implements ProjectsSourceConfigurationService {
    private final ProjectsSourceRepository repository;
    private final DomainMessageBroker bus;
    private final ProjectSourceConfigurationFactory factory;

    @Override
    public ProjectSourceConfigurationId addProjectSourceConfiguration(ProjectSourceConfigurationDto configurationDto) {
        Pair<ProjectSourceConfiguration, Collection<DomainEvent>> result = factory.create(configurationDto);

        ProjectSourceConfiguration configuration = result.getLeft();
        repository.save(configuration);
        bus.publish(result.getRight());

        return configuration.getId();
    }

    @Override
    public void synchronizeProjects(ProjectSourceConfigurationId projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(projectSourceConfigurationId)
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.synchronize();
        bus.publish(result);
    }

    @Override
    public void removeProjectSourceConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId) {
        ProjectSourceConfiguration configuration = repository.find(projectSourceConfigurationId)
                .orElseThrow(ProjectSourceConfigurationNotFoundException::new);

        Collection<DomainEvent> result = configuration.delete();
        repository.delete(projectSourceConfigurationId);
        bus.publish(result);
    }

    @Override
    public Set<ProjectSourceConfigurationDto> listConfigurations() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<ProjectSourceConfigurationDto> getConfiguration(ProjectSourceConfigurationId projectSourceConfigurationId) {
        throw new UnsupportedOperationException();
    }
}
