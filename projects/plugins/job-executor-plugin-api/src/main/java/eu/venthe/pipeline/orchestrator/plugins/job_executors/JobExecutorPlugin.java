package eu.venthe.pipeline.orchestrator.plugins.job_executors;

import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.JobDefinitionContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.NormalJobExecutionContext;

public interface JobExecutorPlugin {
    void queueJobExecution(JobDefinitionContext jobDefinition, NormalJobExecutionContext context);
}
