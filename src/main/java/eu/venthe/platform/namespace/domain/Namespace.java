package eu.venthe.platform.namespace.domain;

import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class Namespace implements Aggregate<NamespaceName> {
    NamespaceName namespaceName;
    Source source;
    ProjectsSynchronizer projectsSynchronizer;

    Namespace(NamespaceName namespaceName, Source source, MessageBroker messageBroker, ProjectsQueryService projectsQueryService) {
        this.source = source;
        this.namespaceName = namespaceName;
        this.projectsSynchronizer = new ProjectsSynchronizer(source, messageBroker, projectsQueryService, namespaceName);
    }

    public List<DomainTrigger> synchronize() {
        return projectsSynchronizer.synchronize();
    }

    public NamespaceName getId() {
        return namespaceName;
    }

    @RequiredArgsConstructor
    static class Source {
        private final SourceQueryService sourceQueryService;
        @Getter
        private final ConfigurationSourceId configurationSourceId;

        Source(final ConfigurationSourceId configurationSourceId, final SourceQueryService sourceQueryService) {
            this.configurationSourceId = configurationSourceId;
            this.sourceQueryService = sourceQueryService;
        }

        Set<SourceOwnedProjectId> getAllAvailableProjectIds() {
            return sourceQueryService.getProjectIdentifiers(configurationSourceId).collect(Collectors.toSet());
        }
    }
}
