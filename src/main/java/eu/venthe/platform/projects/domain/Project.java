package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.ProjectRegisteredEvent;
import eu.venthe.platform.shared_kernel.DomainResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Project {
    private final Id id;

    public static DomainResult<Project> create(SourceConfigurationRepository sourceConfigurationRepository, String sourceName, String projectName) {
        var sourceConfigurationCandidate = sourceConfigurationRepository.find(sourceName);

        if (!sourceConfigurationCandidate.isPresent()) {
            throw new SourceConfigurationMissingException();
        }
        var sourceConfiguration = sourceConfigurationCandidate.get();

        var repositoryDataCandidate = sourceConfiguration.getRepository(projectName);

        if (!repositoryDataCandidate.isPresent()) {
            throw new RepositoryDataMissingException();
        }
        var repositoryData = repositoryDataCandidate.get();

        var project = new Project(
                new Id(sourceConfiguration.getName(), repositoryData.repositoryName())
        );
        return DomainResult.from(
                project,
                new ProjectRegisteredEvent(project.getId().getName(), project.getId().getSourceConfigurationName())
        );
    }

    public void visit(ProjectVisitor visitor) {
        visitor.setSourceConfigurationName(getId().getSourceConfigurationName());
        visitor.setName(getId().getName());
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static final class Id {
        private final String sourceConfigurationName;
        private final String name;
    }
}
