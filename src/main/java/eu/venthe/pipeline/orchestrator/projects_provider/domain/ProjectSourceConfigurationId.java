package eu.venthe.pipeline.orchestrator.projects_provider.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProjectSourceConfigurationId {
    String value;
}
