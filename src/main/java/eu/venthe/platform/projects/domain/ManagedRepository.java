package eu.venthe.platform.projects.domain;

public record ManagedRepository(
        ProjectCorrelationId correlationId,
        String trackedBranch,
        String trackedBranchHash
) {
}
