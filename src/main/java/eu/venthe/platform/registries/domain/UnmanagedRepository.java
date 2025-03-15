package eu.venthe.platform.registries.domain;

import lombok.Builder;

@Builder
public record UnmanagedRepository(RepositoryCorrelationIdentifier repositoryCorrelationIdentifier,
                                  RegistryIdentifier registryIdentifier) {

    public static class UnmanagedRepositoryBuilder {
        public UnmanagedRepository build(RegistryIdentifier registryIdentifier) {
            return new UnmanagedRepository(repositoryCorrelationIdentifier, registryIdentifier);
        }
    }
}
