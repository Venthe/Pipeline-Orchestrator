/*
package eu.venthe.pipeline.orchestrator.job_executor;

import eu.venthe.pipeline.orchestrator.job_executor.events.JobExecutionQueued;
import eu.venthe.pipeline.orchestrator.plugins.job_executors.JobExecutorPlugin;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class TestExecutorPlugin implements JobExecutorPlugin {
    private final JobExecutorCallbackService callbackService;
    private final List<Runnable> steps = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(1);

    private Runnable afterCompletion;
    private Runnable onStart;

    @Override
    public Collection<DomainEvent> queueJobExecution(String id, String jobId) {
        executorService.execute(() -> {
            if (onStart != null) {
                onStart.run();
            } else {
                callbackService.jobExecutionStarted();
            }

            steps.forEach(Runnable::run);

            if (onStart != null) afterCompletion.run(); else callbackService.jobExecutionCompleted();
        });

        return List.of(new JobExecutionQueued());
    }

    public void addStep(Consumer<JobExecutorCallbackService> consumer) {
        steps.add(() -> consumer.accept(callbackService));
    }

    public void afterCompletion(Consumer<JobExecutorCallbackService> consumer) {
        afterCompletion = () -> consumer.accept(callbackService);
    }

    public void beforeStart(Consumer<JobExecutorCallbackService> consumer) {
        onStart = () -> consumer.accept(callbackService);
    }
}
*/
