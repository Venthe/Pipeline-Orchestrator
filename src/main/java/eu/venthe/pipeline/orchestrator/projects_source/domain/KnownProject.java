package eu.venthe.pipeline.orchestrator.projects_source.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KnownProject {
    private final ProjectId projectId;
    private ProjectStatus status;
}
