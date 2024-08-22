package eu.venthe.platform.runner;

import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.workflow.runs.events.RequestJobRunCommand;
import org.springframework.stereotype.Service;

@Service
public class RunnerListener {
    public RunnerListener(final MessageListenerRegistry registry, final RunnerProvider runnerProvider) {
        registry.register(RequestJobRunCommand.class, new MessageListenerRegistry.Observer<>(RunnerProvider.class.getSimpleName(), envelope -> {
            RequestJobRunCommand runRequest = envelope.getData();

            // FIXME: Get token from the repository
            runnerProvider.queueExecution(runRequest.repositoryId(), runRequest.workflowRunId(), runRequest.runId(), null, RunnerDimensions.none());
        }));
    }
}
