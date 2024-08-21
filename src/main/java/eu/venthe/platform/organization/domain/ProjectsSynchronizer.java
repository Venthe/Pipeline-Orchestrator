package eu.venthe.platform.organization.domain;

import com.google.common.collect.Sets;
import eu.venthe.platform.organization.domain.events.ArchiveProjectCommand;
import eu.venthe.platform.organization.domain.events.CreateProjectCommand;
import eu.venthe.platform.organization.domain.events.SynchronizeProjectCommand;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.domain.model.ConfigurationSourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

record ProjectsSynchronizer(
        Organization.Source source,
        MessageBroker messageBroker,
        ProjectsQueryService projectsQueryService,
        OrganizationName organizationName
) {
    List<DomainTrigger> synchronize() {
        final Set<SourceOwnedProjectId> allProjectsFromSource = getAllAvailableProjectIds();
        final Set<SourceOwnedProjectId> registeredProjects = getAlreadyRegisteredProjects();

        return Stream.<List<DomainTrigger>>builder()
                .add(updateProjects(allProjectsFromSource, registeredProjects))
                .add(archiveProjects(allProjectsFromSource, registeredProjects))
                .add(createProjects(allProjectsFromSource, registeredProjects))
                .build()
                .flatMap(Collection::stream)
                .toList();
    }

    private Set<SourceOwnedProjectId> getAllAvailableProjectIds() {
        return new HashSet<>(source.getAllAvailableProjectIds());
    }

    private Set<SourceOwnedProjectId> getAlreadyRegisteredProjects() {
        return projectsQueryService.getProjectIds(organizationName)
                .map(e -> new SourceOwnedProjectId(source.getConfigurationSourceId(), e.getName()))
                .collect(toSet());
    }

    private List<DomainTrigger> updateProjects(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return getProjectsToUpdate(allProjectsFromSource, registeredProjects)
                .<DomainTrigger>map(SynchronizeProjectCommand::new)
                .toList();
    }

    private Stream<SourceOwnedProjectId> getProjectsToUpdate(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return Sets.intersection(allProjectsFromSource, registeredProjects).stream();
    }

    private List<DomainTrigger> archiveProjects(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return getProjectsToArchive(allProjectsFromSource, registeredProjects)
                .<DomainTrigger>map(ArchiveProjectCommand::new)
                .toList();
    }

    private Stream<SourceOwnedProjectId> getProjectsToArchive(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return Sets.difference(registeredProjects, allProjectsFromSource).stream();
    }

    private List<DomainTrigger> createProjects(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return getProjectsToCreate(allProjectsFromSource, registeredProjects)
                .<DomainTrigger>map(e -> new CreateProjectCommand(organizationName(), e))
                .toList();
    }

    private Stream<SourceOwnedProjectId> getProjectsToCreate(Set<SourceOwnedProjectId> allProjectsFromSource, Set<SourceOwnedProjectId> registeredProjects) {
        return Sets.difference(allProjectsFromSource, registeredProjects).stream();
    }
}
