package eu.venthe.pipeline.orchestrator.modules.automation.runners;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.events.RequestJobRunCommand;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.MessageListenerRegistry;
import org.springframework.stereotype.Service;

@Service
public class RunnerListener {
    public RunnerListener(final MessageListenerRegistry registry, final RunnerProvider runnerProvider) {
        registry.register(RequestJobRunCommand.class, new MessageListenerRegistry.Observer<>(RunnerProvider.class.getSimpleName(), envelope -> {
            var e = envelope.getData();
            runnerProvider.queueExecution(e.projectId(), e.workflowRunId(), e.runId(), e.runCallbackToken(), e.dimensions());
        }));
    }
}
