package eu.venthe.pipeline.orchestrator.projects.domain.model;

import lombok.*;

import java.time.OffsetDateTime;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class KnownProject {
    @EqualsAndHashCode.Include
    @Getter
    private final ProjectId projectId;
    private ProjectStatus status;
    private OffsetDateTime lastUpdated;

    public static KnownProject create(ProjectId projectId, ProjectStatus status) {
        return KnownProject.builder()
                .status(status)
                .projectId(projectId)
                .lastUpdated(OffsetDateTime.now())
                .build();
    }

    public void updateStatus(ProjectStatus status) {
        this.status = status;
        lastUpdated = OffsetDateTime.now();
    }
}
