package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.ProjectRegisteredEvent;
import eu.venthe.platform.shared_kernel.ClockService;
import eu.venthe.platform.shared_kernel.DomainResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@RequiredArgsConstructor
public class Project {
    private final Id id;
    private final SourceConfiguration sourceConfiguration;
    private ZonedDateTime lastUpdate;
    private String trackedBranch;
    private String trackedBranchHash;

    public static DomainResult<Project> create(SourceConfigurationRepository sourceConfigurationRepository,
                                               ClockService clockService,
                                               String sourceName,
                                               String projectName) {
        var sourceConfiguration = sourceConfigurationRepository.find(sourceName)
                .orElseThrow(() -> new SourceConfigurationMissingException(sourceName));

        var repositoryData = sourceConfiguration.getRepository(projectName)
                .orElseThrow(() -> new RepositoryDataMissingException(projectName));

        var project = new Project(
                new Id(sourceConfiguration.getIdentifier(), repositoryData.repositoryName()),
                sourceConfiguration
        );

        project.lastUpdate = ZonedDateTime.now(clockService.getClock());
        project.trackedBranch = repositoryData.trackedBranch();
        project.trackedBranchHash = repositoryData.trackedBranchHash();

        return DomainResult.from(
                project,
                new ProjectRegisteredEvent(project.getId().name(), project.getId().sourceConfigurationName())
        );
    }

    public void visit(ProjectVisitor visitor) {
        visitor.setSourceConfigurationName(getId().sourceConfigurationName());
        visitor.setName(getId().name());
        visitor.setLastUpdated(getLastUpdate());
    }

    public record Id(String sourceConfigurationName, String name) {
    }
}
