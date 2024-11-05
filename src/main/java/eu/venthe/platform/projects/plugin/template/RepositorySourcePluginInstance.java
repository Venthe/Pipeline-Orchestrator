package eu.venthe.platform.projects.plugin.template;

import java.util.Optional;
import java.util.Set;

public interface RepositorySourcePluginInstance {
    String getSourceType();

    Set<Repository> getAllRepositories();

    Optional<Repository> getRepository(String repositoryName);
}
