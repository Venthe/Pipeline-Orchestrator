package eu.venthe.platform.workflow.runs.jobs;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.workflow.definition._archive.steps.StepId;
import eu.venthe.platform.workflow.definition.contexts.JobName;
import eu.venthe.platform.workflow.definition.contexts.WorkflowDefinitionJobsContext;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.dependencies.TimeService;
import eu.venthe.platform.workflow.utilities.GraphUtility;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@ToString
public class JobRuns {
    private final WorkflowDefinitionJobsContext jobs;
    private final TimeService timeService;

    private List<Map<JobName, JobRun>> runs = new ArrayList<>();

    public List<JobRun.RequestJobRunSpecification> run() {
        var lists = GraphUtility.buildDependencyTree(jobs.getJobs().entrySet().stream()
                .map(e -> Map.entry(
                        e.getKey(),
                        e.getValue().getNeeds())
                )
                .map(e -> Map.entry(
                        e.getKey().value(),
                        e.getValue().stream().map(JobName::value).collect(Collectors.toSet()))
                )
                .map(e -> new GraphUtility.JobRequirement(e.getKey(), e.getValue()))
                .collect(Collectors.toSet()));
        for (List<String> list : lists) {
            runs.add(list.stream()
                    .map(JobName::new)
                    .map(this::createRun)
                    .collect(Collectors.toMap(
                            JobRun::getJobId,
                            UnaryOperator.identity()
                    ))
            );
        }
        log.info("{}", runs);

        return runs.getFirst().values().stream().map(JobRun::run).toList();
    }

    private JobRun createRun(JobName id) {
        return new JobRun(id, jobs.getJobs().get(id));
    }

    public List<DomainTrigger> notifyJobStarted(final JobRunId id, final ZonedDateTime date) {
        var collect = runs.stream().map(Map::entrySet).flatMap(Collection::stream).map(Map.Entry::getValue).filter(e -> e.getAttempt().equals(id)).collect(MoreCollectors.onlyElement());
        return collect.notifyJobStarted(date);
    }

    public List<DomainTrigger> notifyJobCompleted(final JobRunId id, final ZonedDateTime date, final Map<String, String> outputs) {
        throw new UnsupportedOperationException();
    }

    public List<DomainTrigger> progress(final JobRunId id, final StepId key, final JobRunStatus value, final ZonedDateTime change) {
        throw new UnsupportedOperationException();
    }
}
