/*
package eu.venthe.pipeline.orchestrator.job_executor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@Slf4j
@Component
@RequiredArgsConstructor
public class TestJobExecutor implements JobExecutor {
    private final WorkflowExecutionService workflowExecutionService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void queueStepped(String workflowExecutionId, String jobId) {
        executorService.execute(() -> {
            try {
                log.info("TestJobExecutor - [{}][{}] Executing", workflowExecutionId, jobId);
                sleep(100);
                workflowExecutionService.updateStatus(workflowExecutionId, new WorkflowExecutionService.WorkflowUpdate(jobId, JobExecutionStatus.IN_PROGRESS));
                sleep(100);
                workflowExecutionService.updateStatus(workflowExecutionId, new WorkflowExecutionService.WorkflowUpdate(jobId, JobExecutionStatus.COMPLETED));

                log.info("TestJobExecutor - [{}][{}] Complete", workflowExecutionId, jobId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
*/
