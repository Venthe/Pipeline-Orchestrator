package eu.venthe.pipeline.orchestrator.modules.automation.workflows;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.WorkflowExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;

import java.nio.file.Path;

public interface ProjectWorkflowCommandService {

    WorkflowExecutionId triggerWorkflowDispatch(final ProjectId id, final Revision revision, final Path workflowPath);
}
