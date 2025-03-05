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
                                               SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                                               String projectName) {
        var sourceConfiguration = sourceConfigurationRepository.find(sourceConfigurationInternalIdentifier)
                .orElseThrow(() -> new SourceConfigurationMissingException(sourceConfigurationInternalIdentifier));

        var repositoryData = sourceConfiguration.getRepository(projectName)
                .orElseThrow(() -> new RepositoryDataMissingException(projectName));

        var project = new Project(
                new Id(sourceConfiguration.getInternalIdentifier(), repositoryData.repositoryName()),
                sourceConfiguration
        );

        project.lastUpdate = ZonedDateTime.now(clockService.getClock());
        project.trackedBranch = repositoryData.trackedBranch();
        project.trackedBranchHash = repositoryData.trackedBranchHash();

        return DomainResult.from(
                project,
                new ProjectRegisteredEvent(
                        project.getId().sourceConfigurationInternalIdentifier(),
                        project.getId().name()
                )
        );
    }

    public void visit(ProjectVisitor visitor) {
        visitor.setSourceConfigurationIdentifier(getId().sourceConfigurationInternalIdentifier());
        visitor.setName(getId().name());
        visitor.setLastUpdated(getLastUpdate());
    }

    public record Id(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier, String name) {
    }
}
