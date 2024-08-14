package eu.venthe.platform.runner;

import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.runs.events.RequestJobRunCommand;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import org.springframework.stereotype.Service;

@Service
public class RunnerListener {
    public RunnerListener(final WorkflowRunQueryService workflowRunQueryService, final MessageListenerRegistry registry, final RunnerProvider runnerProvider) {
        registry.register(RequestJobRunCommand.class, new MessageListenerRegistry.Observer<>(RunnerProvider.class.getSimpleName(), envelope -> {
            // var runRequest = envelope.getData();

            // runnerProvider.queueExecution(runRequest.projectId(), runRequest.workflowRunId(), runRequest.runId(), runRequest.runCallbackToken(), runRequest.dimensions());
        }));
    }
}
