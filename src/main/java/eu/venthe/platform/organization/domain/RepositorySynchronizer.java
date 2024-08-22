package eu.venthe.platform.organization.domain;

import com.google.common.collect.Sets;
import eu.venthe.platform.organization.domain.events.ArchiveRepositoryCommand;
import eu.venthe.platform.organization.domain.events.CreateRepositoryCommand;
import eu.venthe.platform.organization.domain.events.SynchronizeRepositoryCommand;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedRepositoryId;

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
        final Set<SourceOwnedRepositoryId> allRepositoryFromSource = getAllAvailableRepositoryIds();
        final Set<SourceOwnedRepositoryId> registeredRepository = getAlreadyRegisteredRepository();

        return Stream.<List<DomainTrigger>>builder()
                .add(updateRepository(allRepositoryFromSource, registeredRepository))
                .add(archiveRepository(allRepositoryFromSource, registeredRepository))
                .add(createRepository(allRepositoryFromSource, registeredRepository))
                .build()
                .flatMap(Collection::stream)
                .toList();
    }

    private Set<SourceOwnedRepositoryId> getAllAvailableRepositoryIds() {
        return new HashSet<>(source.getAllAvailableRepositoryIds());
    }

    private Set<SourceOwnedRepositoryId> getAlreadyRegisteredRepository() {
        return repositorysQueryService.getRepositoryIds(organizationName)
                .map(e -> new SourceOwnedRepositoryId(source.getConfigurationSourceId(), e.name()))
                .collect(toSet());
    }

    private List<DomainTrigger> updateRepository(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return getRepositoryToUpdate(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(SynchronizeRepositoryCommand::new)
                .toList();
    }

    private Stream<SourceOwnedRepositoryId> getRepositoryToUpdate(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return Sets.intersection(allRepositoryFromSource, registeredRepository).stream();
    }

    private List<DomainTrigger> archiveRepository(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return getRepositoryToArchive(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(ArchiveRepositoryCommand::new)
                .toList();
    }

    private Stream<SourceOwnedRepositoryId> getRepositoryToArchive(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return Sets.difference(registeredRepository, allRepositoryFromSource).stream();
    }

    private List<DomainTrigger> createRepository(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return getRepositoryToCreate(allRepositoryFromSource, registeredRepository)
                .<DomainTrigger>map(e -> new CreateRepositoryCommand(organizationName(), e))
                .toList();
    }

    private Stream<SourceOwnedRepositoryId> getRepositoryToCreate(Set<SourceOwnedRepositoryId> allRepositoryFromSource, Set<SourceOwnedRepositoryId> registeredRepository) {
        return Sets.difference(allRepositoryFromSource, registeredRepository).stream();
    }
}
