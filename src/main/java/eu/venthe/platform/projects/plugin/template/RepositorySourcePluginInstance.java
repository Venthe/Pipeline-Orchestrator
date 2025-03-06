package eu.venthe.platform.projects.plugin.template;

import eu.venthe.platform.projects.domain.ProjectCorrelationId;

import java.util.Optional;
import java.util.Set;

public interface RepositorySourcePluginInstance {
    String getSourceType();

    Set<Repository> getAllRepositories();

    Optional<Repository> getRepository(ProjectCorrelationId projectCorrelationId);
}
