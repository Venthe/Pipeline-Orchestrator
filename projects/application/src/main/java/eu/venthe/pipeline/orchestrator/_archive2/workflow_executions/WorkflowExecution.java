package eu.venthe.pipeline.orchestrator._archive2.workflow_executions;

import eu.venthe.pipeline.orchestrator.plugins.job_executors.JobExecutor;
import com.google.common.collect.MoreCollectors;
import eu.venthe.pipeline.orchestrator._archive2.events.HandledEvent;
import eu.venthe.pipeline.orchestrator._archive2.workflows.Workflow;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.UnaryOperator;

import static eu.venthe.pipeline.orchestrator._archive2.workflow_executions.JobExecutionStatus.*;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@Slf4j
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class WorkflowExecution {
    @EqualsAndHashCode.Include
    private final String id = UUID.randomUUID().toString();
    private final HandledEvent event;
    private final Workflow workflow;

    private List<Map<String, JobState>> jobStates = new ArrayList<>();

    public static Optional<WorkflowExecution> from(Workflow workflow, HandledEvent event) {
        boolean shouldCreateWorkflowExecution = workflow.on(event);
        if (!shouldCreateWorkflowExecution) return Optional.empty();

        return Optional.of(new WorkflowExecution(event, workflow));
    }

//    public String getRunName(ExpressionUtilities expressionUtilities, ObjectMapper objectMapper) {
//        ObjectNode objectNode = objectMapper.createObjectNode();
//
//        ObjectNode internal = objectMapper.createObjectNode();
//        internal.set("event", this.event.getRaw());
//
//        objectNode.set("internal", internal);
//
//        InputsContext.create(event.getRaw()).ifPresent(inputsContext -> objectNode.set("inputs", inputsContext.getRoot()));
//
//        return workflow.getRunName()
//                .map(workflowRunName -> expressionUtilities.evaluate(workflowRunName, objectNode))
//                .orElse(event.getRunName());
//    }
//
//    private ObjectNode getInternalContext(ObjectMapper objectMapper) {
//        ObjectNode internal = objectMapper.createObjectNode();
//        internal.set("event", this.event.getRaw());
//        internal.set("eventName", objectMapper.getNodeFactory().textNode(this.event.getName()));
//        internal.set("retentionDays", objectMapper.getNodeFactory().numberNode(-1));
//        internal.set("runId", objectMapper.getNodeFactory().numberNode(getRunId()));
//        internal.set("runNumber", objectMapper.getNodeFactory().numberNode(getRunNumber()));
//        internal.set("runAttempt", objectMapper.getNodeFactory().numberNode(getRunAttempt()));
//        return internal;
//    }

    public void start(JobExecutor jobExecutor) {
        if (!jobStates.isEmpty()) {
            log.info("Workflow execution already started");
            return;
        }

        log.info("Running workflow execution {}", this);

        List<List<String>> jobOrder = workflow.getJobs().getTree();

        jobOrder.forEach(jobSet -> {
            jobStates.add(jobSet.stream().collect(toMap(UnaryOperator.identity(), jobId -> JobState.initialize(jobId, workflow.getJobs().getJob(jobId)))));
        });

        log.info("Prepared dependency tree {}", jobStates);
        jobStates.get(0).values()
                .forEach(state -> state.queue(jobId -> {
                    log.info("[{}][{}] Executing", getId(), jobId);
                    jobExecutor.queueStepped(getId(), jobId);
                }));
    }

    public WorkflowExecutionStatus getStatus() {
        Set<JobExecutionStatus> collect = jobStates.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .map(JobState::getStatus)
                .collect(toSet());

        if (collect.stream().anyMatch(CANCELLED::equals)) {
            return WorkflowExecutionStatus.CANCELLED;
        }

        if (collect.stream().anyMatch(FAILURE::equals)) {
            return WorkflowExecutionStatus.FAILURE;
        }

        if (collect.stream().anyMatch(e -> Set.of(QUEUED, IN_PROGRESS, WAITING).contains(e))) {
            return WorkflowExecutionStatus.IN_PROGRESS;
        }

        if (Set.of(COMPLETED, SUCCESS, SKIPPED, NEUTRAL).containsAll(collect)) {
            return WorkflowExecutionStatus.COMPLETED;
        }

        throw new UnsupportedOperationException();
    }

    public synchronized void updateJob(String jobId, JobExecutionStatus jobExecutionStatus) {
        log.info("Updating job {} for workflow {} to {}", jobId, getId(), jobExecutionStatus);
        jobStates.stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .filter(e -> e.getId().equals(jobId))
                .collect(MoreCollectors.toOptional())
                .orElseThrow()
                .setStatus(jobExecutionStatus);
    }

    public NewJobsDto getNewJobs() {
        NewJobsDto newJobsDto = new NewJobsDto();

        List<List<String>> jobOrder = workflow.getJobs().getTree();

        jobOrder.forEach(jobSet -> {
            jobStates.add(jobSet.stream().collect(toMap(UnaryOperator.identity(), jobId -> JobState.initialize(jobId, workflow.getJobs().getJob(jobId)))));
        });
        newJobsDto.addJob();
        return newJobsDto;
    }

    @Getter
    public class NewJobsDto {
        Set<JobDto> jobs = new HashSet<>();

        public void addJob() {

        }

        @Getter
        @EqualsAndHashCode(onlyExplicitlyIncluded = true)
        @RequiredArgsConstructor
        @ToString
        public class JobDto {
            @EqualsAndHashCode.Include
            private final String id;
        }
    }
}
