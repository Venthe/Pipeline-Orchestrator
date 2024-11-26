package eu.venthe.platform.projects.domain;

public record ManagedRepository(
        String repositoryName,
        String trackedBranch,
        String trackedBranchHash
) {
}
