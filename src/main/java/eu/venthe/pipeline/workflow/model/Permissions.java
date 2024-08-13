package eu.venthe.pipeline.workflow.model;

public enum Permissions {
    /**
     * Work with GitHub Actions. For example, actions: write permits an action to cancel a workflow run. For more information, see "Permissions required for GitHub Apps."
     */
    ACTIONS,
    /**
     * Work with artifact attestations. For example, attestations: write permits an action to generate an artifact attestation for a build. For more information, see "Using artifact attestations to establish provenance for builds"
     */
    ATTESTATIONS,
    /**
     * Work with check runs and check suites. For example, checks: write permits an action to create a check run. For more information, see "Permissions required for GitHub Apps."
     */
    CHECKS,
    /**
     * Work with the contents of the repository. For example, contents: read permits an action to list the commits, and contents: write allows the action to create a release. For more information, see "Permissions required for GitHub Apps."
     */
    CONTENTS,
    /**
     * Work with deployments. For example, deployments: write permits an action to create a new deployment. For more information, see "Permissions required for GitHub Apps."
     */
    DEPLOYMENTS,
    /**
     * Work with GitHub Discussions. For example, discussions: write permits an action to close or delete a discussion. For more information, see "Using the GraphQL API for Discussions."
     */
    DISCUSSIONS,
    /**
     * Fetch an OpenID Connect (OIDC) token. This requires id-token: write. For more information, see "About security hardening with OpenID Connect"
     */
    ID_TOKEN,
    /**
     * Work with issues. For example, issues: write permits an action to add a comment to an issue. For more information, see "Permissions required for GitHub Apps."
     */
    ISSUES,
    /**
     * Work with GitHub Packages. For example, packages: write permits an action to upload and publish packages on GitHub Packages. For more information, see "About permissions for GitHub Packages."
     */
    PACKAGES,
    /**
     * Work with GitHub Pages. For example, pages: write permits an action to request a GitHub Pages build. For more information, see "Permissions required for GitHub Apps."
     */
    PAGES,
    /**
     * Work with pull requests. For example, pull-requests: write permits an action to add a label to a pull request. For more information, see "Permissions required for GitHub Apps."
     */
    PULL_REQUESTS,
    /**
     * Work with GitHub projects (classic). For example, repository-projects: write permits an action to add a column to a project (classic). For more information, see "Permissions required for GitHub Apps."
     */
    REPOSITORY_PROJECTS,
    /**
     * Work with GitHub code scanning and Dependabot alerts. For example, security-events: read permits an action to list the Dependabot alerts for the repository, and security-events: write allows an action to update the status of a code scanning alert. For more information, see "Repository permissions for 'Code scanning alerts'" and "Repository permissions for 'Dependabot alerts'" in "Permissions required for GitHub Apps."
     */
    SECURITY_EVENTS,
    /**
     * Work with commit statuses. For example, statuses:read permits an action to list the commit statuses for a given reference. For more information, see "Permissions required for GitHub Apps."
     */
    STATUSES;

    public enum Value {
        READ,
        WRITE,
        NONE;
    }

    public record Permission(Permissions permission, Value value) {}
}
