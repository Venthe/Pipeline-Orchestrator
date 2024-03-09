package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain;

import lombok.Getter;
import org.togglz.core.Feature;
import org.togglz.core.util.NamedFeature;

@Getter
public enum ProjectsSourceFeatureFlags {
    PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP("PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP");

    private final Feature feature;

    ProjectsSourceFeatureFlags(String key) {
        feature = new NamedFeature(key);
    }
}
