package eu.venthe.pipeline.runners;

import eu.venthe.pipeline.workflows.WorkflowRunQueryService;
import eu.venthe.pipeline.workflows.runs.events.RequestJobRunCommand;
import eu.venthe.pipeline.shared_kernel.events.MessageListenerRegistry;
import org.springframework.stereotype.Service;

@Service
public class RunnerListener {
    public RunnerListener(final WorkflowRunQueryService workflowRunQueryService, final MessageListenerRegistry registry, final RunnerProvider runnerProvider) {
        registry.register(RequestJobRunCommand.class, new MessageListenerRegistry.Observer<>(RunnerProvider.class.getSimpleName(), envelope -> {
            var runRequest = envelope.getData();

            workflowRunQueryService.getContext();

            runnerProvider.queueExecution(runRequest.projectId(), runRequest.workflowRunId(), runRequest.runId(), runRequest.runCallbackToken(), runRequest.dimensions());
        }));
    }
}