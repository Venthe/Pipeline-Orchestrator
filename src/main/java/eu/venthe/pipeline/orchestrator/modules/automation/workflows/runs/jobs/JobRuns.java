package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.utilities.GraphUtility;
import eu.venthe.pipeline.orchestrator.utilities.EnvUtil;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ToString
public class JobRuns {
    private final JobsContext jobs;

    private List<Map<JobId, JobRun>> runs;

    public void start(final WorkflowExecutionCommandService.Context context, final EnvUtil util) {
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
        runs.get(0).values().forEach(e -> e.run());
    }

    private JobRun createRun(final JobId id) {
        throw new UnsupportedOperationException();
    }
}
