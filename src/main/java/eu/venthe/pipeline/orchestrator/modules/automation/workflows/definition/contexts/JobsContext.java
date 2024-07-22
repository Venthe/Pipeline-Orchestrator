package eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.contexts.jobs.JobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.utilities.CollectionUtilities;

import java.util.Map;
import java.util.stream.Collectors;

// TODO: Implement context
public class JobsContext {
    private final Map<JobId, JobContext> jobs;

    private JobsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        jobs = CollectionUtilities.iteratorToStream(root.fields())
                .collect(Collectors.toMap(
                        e -> new JobId(e.getKey()),
                        e -> JobContext.ensure(e.getValue())
                ));
    }

    public static JobsContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, JobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    }

    // private final Map<String, ?> jobs;
    // @Getter
    // private final List<List<String>> dependencyTree;
    //
    // public JobsContext(JsonNode _root) {
    //     ObjectNode root = ContextUtilities.validateIsObjectNode(_root);
    //
    //     this.jobs = root.properties().stream()
    //             .map(e -> Map.entry(e.getKey(), BaseJobContext.ensure(e.getValue())))
    //             .collect(Collectors.toMap(
    //                     Map.Entry::getKey,
    //                     Map.Entry::getValue
    //             ));
    //
    //     Set<GraphUtility.JobRequirements> jobRequirements = jobs.entrySet().stream()
    //             .map(e -> new GraphUtility.JobRequirements(e.getKey(), new HashSet<>(e.getValue().getNeeds())))
    //             .collect(Collectors.toSet());
    //
    //     this.dependencyTree = GraphUtility.buildDependencyTree(jobRequirements);
    //
    // }
    //
    // public static JobsContext ensure(JsonNode root) {
    //     return ContextUtilities.ensure(root, JobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    // }
    //
    // public Object getJob(String jobId) {
    //     return jobs.get(jobId);
    // }
}
