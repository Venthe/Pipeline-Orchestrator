package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.git.Revision;
import eu.venthe.platform.workflow.definition.WorkflowDefinition;

import java.nio.file.Path;

public record WorkflowData(WorkflowDefinition workflowDefinition, Revision revision, Path path, OrganizationName organizationName, RepositoryName repositoryName) {
}
