package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.ProjectRegisteredEvent;
import eu.venthe.platform.shared_kernel.DomainResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.ZonedDateTime;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@RequiredArgsConstructor
public class Project {
    private final ProjectCorrelationId projectCorrelationId;
    private final SourceConfiguration sourceConfiguration;
    private ZonedDateTime lastUpdate;
    private String trackedBranch;
    private String trackedBranchHash;

    public static DomainResult<Project> create(SourceConfigurationRepository sourceConfigurationRepository,
                                               Clock clock,
                                               SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                                               ProjectCorrelationId projectCorrelationId) {
        var sourceConfiguration = sourceConfigurationRepository.find(sourceConfigurationInternalIdentifier)
                .orElseThrow(() -> new SourceConfigurationMissingException(sourceConfigurationInternalIdentifier));

        var repositoryData = sourceConfiguration.getRepository(projectCorrelationId)
                .orElseThrow(() -> new RepositoryDataMissingException(sourceConfigurationInternalIdentifier, projectCorrelationId));

        var project = new Project(
                repositoryData.correlationId(),
                sourceConfiguration
        );

        project.lastUpdate = ZonedDateTime.now(clock);
        project.trackedBranch = repositoryData.trackedBranch();
        project.trackedBranchHash = repositoryData.trackedBranchHash();

        return DomainResult.from(
                project,
                new ProjectRegisteredEvent(
                        project.getSourceConfigurationInternalIdentifier(),
                        project.getProjectCorrelationId()
                )
        );
    }

    public SourceConfigurationInternalIdentifier getSourceConfigurationInternalIdentifier() {
        return sourceConfiguration.getInternalIdentifier();
    }

    @EqualsAndHashCode.Include
    public Id getId() {
        return new Id(getSourceConfigurationInternalIdentifier(), getProjectCorrelationId());
    }

    public void visit(ProjectVisitor visitor) {
        visitor.setSourceConfigurationIdentifier(getSourceConfigurationInternalIdentifier());
        visitor.setCorrelationId(getProjectCorrelationId());
        visitor.setLastUpdated(getLastUpdate());
    }

    public record Id(SourceConfigurationInternalIdentifier sourceConfigurationInternalIdentifier,
                     ProjectCorrelationId projectCorrelationId) {
    }
}
