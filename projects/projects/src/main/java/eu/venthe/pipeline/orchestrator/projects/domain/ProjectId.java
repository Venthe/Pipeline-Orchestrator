package eu.venthe.pipeline.orchestrator.projects.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectId {
    String systemId;
    String id;
}
