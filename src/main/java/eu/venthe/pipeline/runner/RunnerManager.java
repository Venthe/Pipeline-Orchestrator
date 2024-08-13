package eu.venthe.pipeline.runner;

import eu.venthe.pipeline.runner.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.runner.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.runner.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.runner.runner_engine.template.model.dimensions.RunnerDimensions;

public interface RunnerManager extends RunnerProvider {

    RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification);

    RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                            RunnerDimensions dimensions);

}
