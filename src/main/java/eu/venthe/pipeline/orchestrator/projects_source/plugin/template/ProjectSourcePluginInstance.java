package eu.venthe.pipeline.orchestrator.projects_source.plugin.template;

import lombok.Value;

@Value
public class ProjectSourcePluginInstance implements ProjectSourcePlugin.PluginInstance {
    ProjectsProvider projectsProvider;
    ProjectDataProvider projectDataProvider;
}
