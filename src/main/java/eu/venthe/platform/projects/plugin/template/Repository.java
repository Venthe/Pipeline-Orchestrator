package eu.venthe.platform.projects.plugin.template;

import eu.venthe.platform.projects.domain.ProjectCorrelationId;

public record Repository(ProjectCorrelationId correlationId, String trackedBranch, String trackedBranchHash) {
}
