package eu.venthe.pipeline.orchestrator.projects.application;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceFeatureFlags;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProjectsServiceImpl implements ProjectsService {
    private final FeatureManager featureManager;
    private final ProjectRepository projectRepository;

    @Override
    public Collection<ProjectDto> listProjects() {
        if (featureManager.isActive(ProjectsSourceFeatureFlags.PROJECTS_SERVICE_WIP.getFeature())) {
            return Collections.emptyList();
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public void addProject(NewProjectDto newProjectDto) {
        throw new UnsupportedOperationException();
    }
}
