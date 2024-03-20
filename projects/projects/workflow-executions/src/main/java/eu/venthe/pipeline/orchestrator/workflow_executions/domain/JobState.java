package eu.venthe.pipeline.orchestrator.workflow_executions.domain;

import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.BaseJobContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.function.Consumer;

@Slf4j
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
@Getter
public class JobState {
    private final String id;
    private OffsetDateTime startDate;
    private final BaseJobContext job;
    private JobExecutionStatus status = JobExecutionStatus.WAITING;
    private OffsetDateTime endTime;

    public static JobState initialize(String id, BaseJobContext context) {
        return new JobState(id, context);
    }

    public void queue(Consumer<String> execute) {
        startDate = OffsetDateTime.now();
        status = JobExecutionStatus.QUEUED;

        execute.accept(id);
    }

    public void setStatus(JobExecutionStatus jobExecutionStatus) {
        this.status = jobExecutionStatus;
    }
}
