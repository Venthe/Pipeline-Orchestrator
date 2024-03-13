package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import eu.venthe.pipeline.orchestrator.plugins.projects.Project;
import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectProvider;

import java.util.Collection;

public class GerritProjectProvider implements ProjectProvider {
    @Override
    public Collection<Project> getProjects() {
        throw new UnsupportedOperationException();
    }
}
