package eu.venthe.pipeline.orchestrator.projects_provider.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.projects.ProjectStatus;
import lombok.Value;

@Value
public class KnownProject {
    String id;
    String systemId;
    ProjectStatus status;
}
