package eu.venthe.platform.projects.plugin.template;

import java.util.Set;

public interface RepositorySourcePluginInstance {
    String getSourceType();

    Set<Repository> getAllRepositories();
}
