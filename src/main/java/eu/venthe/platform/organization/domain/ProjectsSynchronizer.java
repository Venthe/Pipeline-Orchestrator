package eu.venthe.platform.organization.domain;

import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectDto;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import org.jgrapht.alg.util.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static eu.venthe.platform.project.domain.ProjectStatus.ARCHIVED;
import static java.util.stream.Collectors.toSet;

record ProjectsSynchronizer(Sources sources, MessageBroker messageBroker) {
    List<DomainTrigger> synchronize() {
        final Set<String> allProjectsFromSource = getAllProjectsFromSource();
        final Set<String> registeredProjects = getRegisteredProjects();

        updateProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
        archiveProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
        createProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
    }

    private Set<String> getAllProjectsFromSource() {
        sources.each

        return projectsProvider().listProjectsFromSource()
                .map(ProjectId::getName)
                .collect(toSet());
    }

    private Set<String> getRegisteredProjects() {
        return projectsProvider().listRegisteredProjects()
                .map(ProjectId::getName)
                .collect(toSet());
    }

    private void updateProjects(ProjectsCommandService projectsCommandService, Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        getProjectsToUpdate(allProjectsFromSource, registeredProjects)
                .forEach(projectsCommandService::synchronize);
    }

    private Stream<ProjectId> getProjectsToUpdate(Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        return Sets.intersection(allProjectsFromSource, registeredProjects).stream()
                .map(this::buildProjectId);
    }

    private void archiveProjects(ProjectsCommandService projectsCommandService, Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        getProjectsToArchive(allProjectsFromSource, registeredProjects)
                .forEach(projectId -> projectsCommandService.changeStatus(projectId, ARCHIVED));
    }

    private Stream<ProjectId> getProjectsToArchive(Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        return Sets.difference(registeredProjects, allProjectsFromSource).stream()
                .map(this::buildProjectId);
    }

    private void createProjects(ProjectsCommandService projectsCommandService, Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        getProjectsToCreate(allProjectsFromSource, registeredProjects)
                .map(projectId -> Pair.of(projectId, projectIdToProjectDto(projectId)))
                .map(entry -> new CreateProjectSpecificationDto(entry.getFirst(), entry.getSecond().getStatus()))
                .forEach(newProjectDto -> projectsCommandService.add(configuration.getConfigurationId(), newProjectDto));
    }

    private Stream<eu.venthe.platform.organization.domain.source_configuration.plugins.template.model> getProjectsToCreate(Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        return Sets.difference(allProjectsFromSource, registeredProjects).stream()
                .map(this::buildProjectId);
    }

    private ProjectDto projectIdToProjectDto(ProjectId projectId) {
        return configuration.getProject(projectId.getName()).orElseThrow();
    }

    private ProjectId buildProjectId(String projectId) {
        return ProjectId.builder().configurationId(configuration.getId()).name(projectId).build();
    }

    public interface ProjectsProvider {
        Stream<ProjectId> listProjectsFromSource();
        Stream<ProjectId> listRegisteredProjects();
    }
}
