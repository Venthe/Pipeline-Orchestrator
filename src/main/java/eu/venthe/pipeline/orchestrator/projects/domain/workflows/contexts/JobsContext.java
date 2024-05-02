package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.GraphUtility;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// TODO: Implement context
public class JobsContext {
    private final Map<String, ?> jobs;
    @Getter
    private final List<List<String>> dependencyTree;

    public JobsContext(JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        this.jobs = root.properties().stream()
                .map(e -> Map.entry(e.getKey(), BaseJobContext.ensure(e.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue
                ));

        Set<GraphUtility.JobRequirements> jobRequirements = jobs.entrySet().stream()
                .map(e -> new GraphUtility.JobRequirements(e.getKey(), new HashSet<>(e.getValue().getNeeds())))
                .collect(Collectors.toSet());

        this.dependencyTree = GraphUtility.buildDependencyTree(jobRequirements);

    }

    public static JobsContext ensure(JsonNode root) {
        return ContextUtilities.ensure(root, JobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    }

    public Object getJob(String jobId) {
        return jobs.get(jobId);
    }
}
