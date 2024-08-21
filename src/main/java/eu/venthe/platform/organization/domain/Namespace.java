package eu.venthe.platform.organization.domain;

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
public class Organization implements Aggregate<OrganizationName> {
    OrganizationName organizationName;
    Source source;
    ProjectsSynchronizer projectsSynchronizer;

    Organization(OrganizationName organizationName, Source source, MessageBroker messageBroker, ProjectsQueryService projectsQueryService) {
        this.source = source;
        this.organizationName = organizationName;
        this.projectsSynchronizer = new ProjectsSynchronizer(source, messageBroker, projectsQueryService, organizationName);
    }

    public List<DomainTrigger> synchronize() {
        return projectsSynchronizer.synchronize();
    }

    public OrganizationName getId() {
        return organizationName;
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
