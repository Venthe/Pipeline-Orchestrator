package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.impl.vo.RegisterAdapterSpecification;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model.RunnerId;

import java.util.Optional;

public interface ExecutionAdapterManager {

    AdapterId registerAdapter(RegisterAdapterSpecification specification);

    RunnerId registerRunner(AdapterId adapterId,
                            RunnerDimensions dimensions);

    Optional<JobExecutorAdapter.AdapterInstance> queryAdapter(AdapterId aDefault);
}
