package eu.venthe.platform.organization.domain;

import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;
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
    RepositorySynchronizer repositorysSynchronizer;

    Organization(OrganizationName organizationName, Source source, MessageBroker messageBroker, RepositoryQueryService repositorysQueryService) {
        this.source = source;
        this.organizationName = organizationName;
        this.repositorysSynchronizer = new RepositorySynchronizer(source, messageBroker, repositorysQueryService, organizationName);
    }

    public List<DomainTrigger> synchronize() {
        return repositorysSynchronizer.synchronize();
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

        Set<SourceOwnedRepositoryName> getAllAvailableRepositoryIds() {
            return sourceQueryService.getRepositoryIdentifiers(configurationSourceId).collect(Collectors.toSet());
        }
    }
}
