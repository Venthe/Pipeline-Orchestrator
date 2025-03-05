package eu.venthe.platform.projects.domain;

import java.time.ZonedDateTime;

public interface ProjectVisitor {
    void setSourceConfigurationIdentifier(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier);

    void setName(String name);

    void setLastUpdated(ZonedDateTime lastUpdate);
}
