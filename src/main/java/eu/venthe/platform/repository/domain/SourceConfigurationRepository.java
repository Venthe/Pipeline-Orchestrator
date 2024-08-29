package eu.venthe.platform.repository.domain;

import java.util.Optional;

public interface SourceConfigurationRepository {
    boolean exists(String name);

    void save(SourceConfiguration sourceConfiguration);

    Optional<SourceConfiguration> find(String name);
}
