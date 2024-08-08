package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.runners.RunnerProvider;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.ExecutionCallbackToken;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.Dimension;
import eu.venthe.pipeline.orchestrator.modules.automation.runners.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities.GraphUtility;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ToString
public class JobRuns {
    private final JobsContext jobs;
    @ToString.Exclude
    private final WorkflowRun workflowRun;
    private final TimeService timeService;

    private List<Map<JobId, JobRun>> runs = new ArrayList<>();
    private WorkflowExecutionCommandService.Context context;

    public void start(final RunnerProvider runnerProvider,
                      final WorkflowExecutionCommandService.Context context) {
        this.context = context;
        var lists = GraphUtility.buildDependencyTree(jobs.getJobs().entrySet().stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().getNeeds())
                )
                .map(e -> Map.entry(
                        e.getKey().value(),
                        e.getValue().stream().map(JobId::value).collect(Collectors.toSet()))
                )
                .map(e -> new GraphUtility.JobRequirement(e.getKey(), e.getValue()))
                .collect(Collectors.toSet()));
        for (List<String> list : lists) {
            runs.add(list.stream()
                    .map(JobId::new)
                    .map(this::createRun)
                    .collect(Collectors.toMap(
                            JobRun::getJobId,
                            UnaryOperator.identity()
                    ))
            );
        }
        log.info("{}", runs);
        runs.get(0).values().forEach(e -> e.run((JobRunId executionId, ExecutionCallbackToken token) -> {
            runnerProvider.queueExecution(context.id(), executionId, token, RunnerDimensions.builder().from(context.dimensions().toArray(Dimension[]::new)).build());
            return null;
        }));
    }

    private JobRun createRun(final JobId id) {
        return new JobRun(context, Pair.of(id, jobs.getJobs().get(id)), workflowRun, timeService);
    }
}
