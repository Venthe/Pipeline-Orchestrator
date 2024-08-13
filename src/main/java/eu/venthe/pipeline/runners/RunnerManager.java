package eu.venthe.pipeline.runners;

import eu.venthe.pipeline.runners.model.RegisterRunnerEngineInstanceSpecification;
import eu.venthe.pipeline.runners.model.RunnerEngineInstanceId;
import eu.venthe.pipeline.runners.runner_engine.template.model.RunnerId;
import eu.venthe.pipeline.runners.runner_engine.template.model.dimensions.RunnerDimensions;

public interface RunnerManager extends RunnerProvider {

    RunnerEngineInstanceId registerRunnerEngineInstance(RegisterRunnerEngineInstanceSpecification specification);

    RunnerId registerRunner(RunnerEngineInstanceId runnerEngineInstanceId,
                            RunnerDimensions dimensions);

}
