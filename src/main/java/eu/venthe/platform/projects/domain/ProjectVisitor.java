package eu.venthe.platform.projects.domain;

import java.time.ZonedDateTime;

public interface ProjectVisitor {
    void setSourceConfigurationName(String sourceConfigurationName);

    void setName(String name);

    void setLastUpdated(ZonedDateTime lastUpdate);
}
