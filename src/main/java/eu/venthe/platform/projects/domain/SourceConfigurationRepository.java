package eu.venthe.platform.projects.domain;

import java.util.Optional;

public interface SourceConfigurationRepository {
    boolean exists(SourceConfigurationInternalIdentifier identifier);

    void save(SourceConfiguration sourceConfiguration);

    Optional<SourceConfiguration> find(SourceConfigurationInternalIdentifier identifier);
}
