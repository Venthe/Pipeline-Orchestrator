package eu.venthe.pipeline.orchestrator.projects_source.plugin.template;

import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.FileDto;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;
import lombok.Value;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Value
public class ProjectSourcePluginInstance implements ProjectSourcePlugin.PluginInstance {
    ProjectsProvider projectsProvider;
    ProjectDataProvider projectDataProvider;

    @Override
    public Optional<byte[]> getFile(String projectIdentifier, String revision, Path path) {
        return projectDataProvider.getFile(projectIdentifier, revision, path);
    }

    @Override
    public Collection<ProjectDto> getProjects() {
        return projectsProvider.getProjects();
    }
}
