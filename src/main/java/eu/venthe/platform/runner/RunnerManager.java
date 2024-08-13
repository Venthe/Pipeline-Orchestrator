package eu.venthe.platform.runner;

import eu.venthe.platform.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.platform.runner.model.RunnerEngineInstanceId;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerId;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;

public interface RunnerManager extends RunnerProvider {

    RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification);

    RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                            RunnerDimensions dimensions);

}
