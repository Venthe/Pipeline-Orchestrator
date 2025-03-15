package eu.venthe.platform.registries.domain.plugins;

import eu.venthe.platform.registries.domain.UnmanagedRepository;

import java.util.Collection;

public interface RegistryPluginInstance {
    RegistryType getType();

    boolean checkHealth();

    Collection<UnmanagedRepository.UnmanagedRepositoryBuilder> getRepositories();
}
