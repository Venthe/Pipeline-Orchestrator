package eu.venthe.pipeline.orchestrator.projects_source.adapter.plugin.git_directories;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectDataAccessService;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.ProjectsProvider;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationProperty;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationPropertyDefinition;

import java.util.Map;

public class GitDirectoriesProjectSourcePlugin implements ProjectSourcePlugin {
    @Override
    public Definition instantiate(Map<String, ConfigurationProperty> properties, Api api) {
        return new Definition() {

            @Override
            public String getSourceType() {
                return "git_directories";
            }

            @Override
            public ProjectsProvider getProjectsProvider() {
                return null;
            }

            @Override
            public ProjectDataAccessService getProjectDataAccessService() {
                return null;
            }
        };
    }

    @Override
    public Map<String, ConfigurationPropertyDefinition> getConfigurationProperties() {
        return Map.of();
    }
}
