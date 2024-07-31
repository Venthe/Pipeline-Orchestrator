package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RegisterRunnerImplementationSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.RunnerImplementationId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.model.RunnerId;

public interface RunnerManager {

    RunnerImplementationId registerRunnerImplementation(RegisterRunnerImplementationSpecification specification);

    RunnerId registerRunner(RunnerImplementationId runnerImplementationId,
                            RunnerDimensions dimensions);
}
