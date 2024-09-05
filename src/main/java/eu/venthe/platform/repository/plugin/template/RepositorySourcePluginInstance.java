package eu.venthe.platform.repository.plugin.template;

import java.util.Set;

public interface RepositorySourcePluginInstance {
    String getSourceType();

    Set<Repository> getAllRepositories();
}
