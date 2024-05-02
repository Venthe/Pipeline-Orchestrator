package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.job_executor.events.JobExecutionCompletedEvent;
import eu.venthe.pipeline.orchestrator.job_executor.events.JobExecutionProgressedEvent;
import eu.venthe.pipeline.orchestrator.job_executor.events.JobExecutionStartedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorCallbackServiceImpl implements JobExecutorCallbackService {
    private final DomainMessageBroker messageBroker;

    @Override
    public void jobExecutionProgressed() {
        messageBroker.publish(new JobExecutionProgressedEvent());
    }

    @Override
    public void jobExecutionStarted() {
        messageBroker.publish(new JobExecutionStartedEvent());
    }

    @Override
    public void jobExecutionCompleted() {
        messageBroker.publish(new JobExecutionCompletedEvent());
    }
}
