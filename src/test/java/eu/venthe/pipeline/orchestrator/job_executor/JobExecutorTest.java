package eu.venthe.pipeline.orchestrator.job_executor;

import eu.venthe.pipeline.orchestrator.job_executor.application.JobExecutorApplicationServiceImpl;
import eu.venthe.pipeline.orchestrator.job_executor.application.JobExecutorCallbackServiceImpl;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
class JobExecutorTest {
    private JobExecutorCommandService commandService;
    private DomainMessageBroker mockMessageBroker;
    private TestExecutorPlugin testExecutorPlugin;
    private JobExecutorCallbackService callbackService;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    @BeforeEach
    void setup() {
        mockMessageBroker = Mockito.mock(DomainMessageBroker.class);
        callbackService = new JobExecutorCallbackServiceImpl(mockMessageBroker);
        testExecutorPlugin = new TestExecutorPlugin(callbackService);
        commandService = new JobExecutorApplicationServiceImpl(mockMessageBroker, testExecutorPlugin);

        Mockito.doAnswer(i -> {
            List<DomainEvent> argument = i.getArgument(0);
            domainEvents.addAll(argument);
            return null;
        }).when(mockMessageBroker).publish(Mockito.anyList());
        Mockito.doAnswer(i -> {
            DomainEvent argument = i.getArgument(0);
            domainEvents.add(argument);
            return null;
        }).when(mockMessageBroker).publish(Mockito.any(DomainEvent.class));
    }

    @Test
    void test1() {
        // given
        testExecutorPlugin.addStep(JobExecutorCallbackService::jobExecutionProgressed);
        testExecutorPlugin.afterCompletion(JobExecutorCallbackService::jobExecutionCompleted);

        // when
        commandService.triggerJobExecution(null);

        // then
        Awaitility.await().untilAsserted(() -> {
            log.info("{}", getCapturedEvents());
        });
    }

    Collection<DomainEvent> getCapturedEvents() {
        return domainEvents;
//        return Stream.concat(
//                domainEventArgumentCaptor.getAllValues().stream(),
//                domainEventListArgumentCaptor.getAllValues().stream().flatMap(Collection::stream)
//        ).toList();
    }
}
