package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobExecutionId;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;

import java.net.URL;

public interface RunnerManager {

    RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification);

    RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                            RunnerDimensions dimensions);

    boolean queueExecution(ProjectId projectId,
                           JobExecutionId executionId,
                           URL systemApiUrl,
                           ExecutionCallbackToken executionCallbackToken,
                           RunnerDimensions dimensions);
}
