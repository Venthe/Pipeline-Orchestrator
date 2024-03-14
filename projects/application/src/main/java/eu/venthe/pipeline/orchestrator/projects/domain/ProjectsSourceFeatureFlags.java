package eu.venthe.pipeline.orchestrator.projects.domain;

import lombok.Getter;
import org.togglz.core.Feature;
import org.togglz.core.util.NamedFeature;

@Getter
public enum ProjectsSourceFeatureFlags {
    PROJECTS_SERVICE_WIP("PROJECTS_SERVICE_WIP");

    private final Feature feature;

    ProjectsSourceFeatureFlags(String key) {
        feature = new NamedFeature(key);
    }
}
