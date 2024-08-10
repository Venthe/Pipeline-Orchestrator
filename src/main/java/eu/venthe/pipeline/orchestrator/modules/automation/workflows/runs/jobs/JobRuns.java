package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.JobId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.WorkflowDefinitionJobsContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.model.JobRunId;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.WorkflowRun;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive.run_context.JobRunContext;
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
    private final WorkflowDefinitionJobsContext jobs;
    @ToString.Exclude
    private final WorkflowRun workflowRun;
    private final TimeService timeService;

    private List<Map<JobId, JobRun>> runs = new ArrayList<>();

    public List<JobRun.RequestJobRunSpecification> run() {
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

        return runs.get(0).values().stream().map(JobRun::run).toList();
    }

    private JobRun createRun(final JobId id) {
        return new JobRun(Pair.of(id, jobs.getJobs().get(id)));
    }

}
