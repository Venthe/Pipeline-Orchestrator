package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.project_configuration_sources.core.domain.ProjectsSourceFeatureFlags;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {
    private final FeatureManager featureManager;

    @Override
    public Collection<ProjectDto> listProjects() {
        if (featureManager.isActive(ProjectsSourceFeatureFlags.PROJECTS_SERVICE_WIP.getFeature())) {
            return Collections.emptyList();
        }

        throw new UnsupportedOperationException();
    }
}
