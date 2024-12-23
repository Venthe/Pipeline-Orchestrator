package eu.venthe.platform.projects.domain;

import java.util.Optional;

public interface SourceConfigurationRepository {
    boolean exists(String identifier);

    void save(SourceConfiguration sourceConfiguration);

    Optional<SourceConfiguration> find(String identifier);
}
