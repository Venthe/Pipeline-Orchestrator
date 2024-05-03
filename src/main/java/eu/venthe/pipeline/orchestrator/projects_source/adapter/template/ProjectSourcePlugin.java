package eu.venthe.pipeline.orchestrator.projects_source.adapter.template;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationProperty;
import eu.venthe.pipeline.orchestrator.projects_source.adapter.template.model.ConfigurationPropertyDefinition;

import java.util.Map;

public interface ProjectSourcePlugin {
    ProjectSourcePlugin.Definition instantiate(Map<String, ConfigurationProperty> properties, Api api);

    Map<String, ConfigurationPropertyDefinition> getConfigurationProperties();

    interface Definition {
        String getSourceType();

        ProjectsProvider getProjectsProvider();

        ProjectDataAccessService getProjectDataAccessService();
    }

    interface Api {
        SystemEventProcessor getSystemEventProcessor();
    }
}
