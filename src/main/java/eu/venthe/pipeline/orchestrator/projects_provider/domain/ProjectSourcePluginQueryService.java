package eu.venthe.pipeline.orchestrator.projects_provider.domain;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.ProjectSourceAdapter;

import java.util.Optional;
import java.util.Set;

public interface ProjectSourcePluginQueryService {
    Set<String> listSystemTypes();

    Optional<ProjectSourceAdapter> getPlugin(String sourceType);
}
