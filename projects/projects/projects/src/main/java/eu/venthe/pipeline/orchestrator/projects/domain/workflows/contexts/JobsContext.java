package eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.projects.domain.utilities.GraphUtility;
import eu.venthe.pipeline.orchestrator.projects.domain.workflows.contexts.jobs.BaseJobContext;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JobsContext {
    private final ObjectNode root;

    public static Optional<JobsContext> create(JsonNode root) {
        return ContextUtilities.get(JobsContext::new, root);
    }

    public static JobsContext ensure(JsonNode root) {
        return create(root).orElseThrow(() -> new IllegalArgumentException("Jobs must exist"));
    }

    public List<List<String>> getTree() {
        if(!root.isObject()) throw new RuntimeException();

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
