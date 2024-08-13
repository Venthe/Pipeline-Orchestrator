package eu.venthe.pipeline.project.domain.source_configurations;

import com.google.common.collect.Sets;
import eu.venthe.pipeline.project.application.dto.CreateProjectSpecificationDto;
import eu.venthe.pipeline.project.application.ProjectsCommandService;
import eu.venthe.pipeline.project.application.ProjectsQueryService;
import eu.venthe.pipeline.project.domain.ProjectId;
import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.ProjectSourcePlugin;
import eu.venthe.pipeline.project.domain.source_configurations.plugins.template.model.ProjectDto;
import org.jgrapht.alg.util.Pair;

import java.util.Set;
import java.util.stream.Stream;

import static eu.venthe.pipeline.project.domain.ProjectStatus.ARCHIVED;
import static java.util.stream.Collectors.toSet;

record ProjectsSourceConfigurationSynchronizer(
        ProjectsSourceConfiguration configuration,
        ProjectSourcePlugin.PluginInstance pluginInstance, ProjectsCommandService projectsCommandService,
        ProjectsQueryService projectsQueryService) {
    public void synchronize() {
        final Set<String> allProjectsFromSource = getAllProjectsFromSource();
        final Set<String> registeredProjects = getRegisteredProjects(projectsQueryService);

        updateProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
        archiveProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
        createProjects(projectsCommandService, allProjectsFromSource, registeredProjects);
    }

    private Set<String> getAllProjectsFromSource() {
        return pluginInstance.getProjectIds()
                .map(ProjectDto.Id::id)
                .map(this::buildProjectId)
                .map(ProjectId::getName)
                .collect(toSet());
    }

    private Set<String> getRegisteredProjects(ProjectsQueryService projectsQueryService) {
        return projectsQueryService.getProjectIds(configuration.getId())
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

    private Stream<ProjectId> getProjectsToCreate(Set<String> allProjectsFromSource, Set<String> registeredProjects) {
        return Sets.difference(allProjectsFromSource, registeredProjects).stream()
                .map(this::buildProjectId);
    }

    private ProjectDto projectIdToProjectDto(ProjectId projectId) {
        return configuration.getProject(projectId.getName()).orElseThrow();
    }

    private ProjectId buildProjectId(String projectId) {
         return ProjectId.builder().configurationId(configuration.getId()).name(projectId).build();
    }
}
