package eu.venthe.pipeline.orchestrator.projects_source.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectSourceConfigurationId {
    String value;
}
