package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerId;

public interface RunnerManager {

    RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification);

    RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                            RunnerDimensions dimensions);
}
