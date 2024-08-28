package eu.venthe.platform.organization.domain;

import com.google.common.collect.Sets;
import eu.venthe.platform.organization.domain.events.ArchiveRepositoryCommand;
import eu.venthe.platform.organization.domain.events.CreateRepositoryCommand;
import eu.venthe.platform.organization.domain.events.SynchronizeRepositoryCommand;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryName;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

record RepositorySynchronizer(
        Organization.Source source,
        MessageBroker messageBroker,
        RepositoryQueryService repositorysQueryService,
        OrganizationName organizationName
) {
    List<DomainTrigger> synchronize() {
        final Set<SourceOwnedRepositoryName> allRepositoryFromSource = getAllAvailableRepositoryIds();
        final Set<SourceOwnedRepositoryName> registeredRepository = getAlreadyRegisteredRepository();

        return Stream.<List<DomainTrigger>>builder()
                .add(updateRepository(allRepositoryFromSource, registeredRepository))
                .add(archiveRepository(allRepositoryFromSource, registeredRepository))
                .add(createRepository(allRepositoryFromSource, registeredRepository))
                .build()
                .flatMap(Collection::stream)
                .toList();
    }

    private Set<SourceOwnedRepositoryName> getAllAvailableRepositoryIds() {
        return new HashSet<>(source.getAllAvailableRepositoryIds());
    }

    private Set<SourceOwnedRepositoryName> getAlreadyRegisteredRepository() {
        return repositorysQueryService.getRepositoryIds(organizationName)
                .map(e -> new SourceOwnedRepositoryName(source.getConfigurationSourceId(), e.name()))
                .collect(toSet());
    }

    private List<DomainTrigger> updateRepository(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return getRepositoryToUpdate(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(SynchronizeRepositoryCommand::new)
                .toList();
    }

    private Stream<SourceOwnedRepositoryName> getRepositoryToUpdate(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return Sets.intersection(allRepositoryFromSource, registeredRepository).stream();
    }

    private List<DomainTrigger> archiveRepository(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return getRepositoryToArchive(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(ArchiveRepositoryCommand::new)
                .toList();
    }

    private Stream<SourceOwnedRepositoryName> getRepositoryToArchive(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return Sets.difference(registeredRepository, allRepositoryFromSource).stream();
    }

    private List<DomainTrigger> createRepository(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return getRepositoryToCreate(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(e -> new CreateRepositoryCommand(organizationName(), e))
                .toList();
    }

    private Stream<SourceOwnedRepositoryName> getRepositoryToCreate(Set<SourceOwnedRepositoryName> allRepositoryFromSource, Set<SourceOwnedRepositoryName> registeredRepository) {
        return Sets.difference(allRepositoryFromSource, registeredRepository).stream();
    }
}
