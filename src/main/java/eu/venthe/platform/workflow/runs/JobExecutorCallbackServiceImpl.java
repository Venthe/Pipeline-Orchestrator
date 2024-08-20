package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.workflow.JobExecutorCallbackService;
import eu.venthe.platform.workflow.definition._archive.steps.StepId;
import eu.venthe.platform.workflow.runs.infrastructure.WorkflowRunRepository;
import eu.venthe.platform.workflow.runs.jobs.JobRunStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobExecutorCallbackServiceImpl implements JobExecutorCallbackService {
    private final WorkflowRunRepository workflowRunRepository;
    private final MessageBroker messageBroker;

    @Override
    public void jobRunProgressed(JobCallbackCallMetadata callMetadata, Map<StepId, Progress> progress) {
        var run = workflowRunRepository.find(callMetadata.workflowRunId()).orElseThrow();
        var triggers = progress.entrySet().stream().map(e -> run.progress(callMetadata.jobRunId(), e.getKey(), e.getValue().status(), e.getValue().time())).flatMap(Collection::stream).map(Envelope::new).collect(Collectors.<Envelope<?>>toUnmodifiableList());
        messageBroker.exchange(triggers);
    }

    @Override
    public void jobRunStarted(JobCallbackCallMetadata callMetadata, ZonedDateTime startedAt) {
        var run = workflowRunRepository.find(callMetadata.workflowRunId()).orElseThrow();
        var triggers = run.notifyJobStarted(callMetadata.jobRunId(), startedAt);
        messageBroker.exchange(Envelope.from(triggers));
    }

    @Override
    public void jobRunCompleted(JobCallbackCallMetadata callMetadata, ZonedDateTime completedAt, Map<String, String> outputs) {
        var run = workflowRunRepository.find(callMetadata.workflowRunId()).orElseThrow();
        var triggers = run.notifyJobCompleted(callMetadata.jobRunId(), completedAt, outputs);
        messageBroker.exchange(Envelope.from(triggers));
    }

    public record Progress(JobRunStatus status, ZonedDateTime time) {
    }
}
