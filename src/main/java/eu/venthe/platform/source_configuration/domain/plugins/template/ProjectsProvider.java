package eu.venthe.platform.source_configuration.domain.plugins.template;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectsProvider {
    Stream<Project> getProjects();

    Stream<SourceProjectId> getProjectIdentifiers();

    Optional<Project> getProject(SourceProjectId id);
}
