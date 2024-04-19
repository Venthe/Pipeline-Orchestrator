package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.GraphUtility;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.BaseJobContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JobsContext {
    private final JsonNode root;

    public static JobsContext ensure(JsonNode root) {
        return ContextUtilities.ensure(root, JobsContext::new, () -> new IllegalArgumentException("Jobs must exist"));
    }

    public List<List<String>> getTree() {
        if (!root.isObject()) {
            throw new RuntimeException();
        }

        Set<GraphUtility.JobRequirements> jobRequirements = root.properties().stream()
                .map(e -> Map.entry(e.getKey(), BaseJobContext.create(e.getValue())))
                .map(e -> new GraphUtility.JobRequirements(e.getKey(), new HashSet<>(e.getValue().getNeeds())))
                .collect(Collectors.toSet());

        return GraphUtility.buildDependencyTree(jobRequirements);
    }

    public BaseJobContext getJob(String jobId) {
        return BaseJobContext.create(this.root.get(jobId));
    }
}
