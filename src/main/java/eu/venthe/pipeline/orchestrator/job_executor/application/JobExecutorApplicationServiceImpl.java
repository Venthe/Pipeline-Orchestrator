package eu.venthe.pipeline.orchestrator.job_executor.application;

import eu.venthe.pipeline.orchestrator.job_executor.JobExecutorCommandService;
import eu.venthe.pipeline.orchestrator.plugins.job_executors.JobExecutorPlugin;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.ddd.ApplicationService;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@ApplicationService
@RequiredArgsConstructor
@Slf4j
public class JobExecutorApplicationServiceImpl implements JobExecutorCommandService {
    private final DomainMessageBroker messageBroker;
    private final JobExecutorPlugin jobExecutorPlugin;

    @Override
    public void triggerJobExecution(Object command) {
        log.debug("Triggering job execution");
        Collection<DomainEvent> domainEvents = jobExecutorPlugin.queueJobExecution(null, null);
        messageBroker.publish(domainEvents);
        log.info("Job execution triggered");
    }
}
