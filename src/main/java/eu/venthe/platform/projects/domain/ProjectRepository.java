package eu.venthe.platform.projects.domain;

import java.util.Optional;

public interface ProjectRepository {

    void save(Project project);

    Optional<Project> find(String sourceName, String repositoryName);
}
