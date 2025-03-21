package eu.venthe.platform.projects.domain;

import java.time.ZonedDateTime;

public interface ProjectVisitor {
    void setSourceConfigurationIdentifier(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier);

    void setCorrelationId(ProjectCorrelationId projectCorrelationId);

    void setLastUpdated(ZonedDateTime lastUpdate);
}
