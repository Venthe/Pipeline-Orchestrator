package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectPlugin;

import java.util.Optional;
import java.util.Set;

public interface ProjectSourcePluginQueryService {
    Set<String> listSystemTypes();

    Optional<ProjectPlugin> getPlugin(String sourceType);
}
