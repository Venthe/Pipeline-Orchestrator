package eu.venthe.platform.runner.model;

import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerId;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;

@RequiredArgsConstructor
@Value
public class RunnerEngineInstanceAggregate implements Aggregate<RunnerEngineInstanceId> {
    RunnerEngineInstanceId id;
    RunnerEngineInstance runnerEngineInstance;

    // TODO: Add logs
    public void queueJobExecution(RepositoryName repositoryId,
                                  WorkflowRunId workflowRunId,
                                  Object executionId,
                                  URL systemApiUrl,
                                  RunCallbackToken runCallbackToken,
                                  RunnerDimensions dimensions) {
        /*var context = new RunnerContext(
                new JobRunRequestContext(
                        new JobRunRequestContext.SystemContext(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                workflowRunId,
                                null,
                                null,
                                null
                        ),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                systemApiUrl,
                runCallbackToken
        );*/
        runnerEngineInstance.queueExecution(null,
                dimensions);
    }

    public RunnerId registerRunner(RunnerDimensions runnerDimensions) {
        return runnerEngineInstance.registerRunner(runnerDimensions);
    }
}
