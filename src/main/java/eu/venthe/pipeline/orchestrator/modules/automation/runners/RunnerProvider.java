package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

public interface RunnerProvider {
    boolean queueExecution(ProjectId projectId,
                           JobRunId executionId,
                           //  URL systemApiUrl,
                           ExecutionCallbackToken executionCallbackToken,
                           RunnerDimensions dimensions);
}
