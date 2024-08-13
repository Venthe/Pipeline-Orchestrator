package eu.venthe.platform.workflow.runs._archive.run_context.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonJobContext {
    // private final String name;
    // private final Optional<NeedsContext> needs;
    // private final Optional<PermissionsContext> permissions;
    // private final Optional<IfContext> _if;
    // private final Optional<ConcurrencyContext> concurrency;
    // private final Optional<DefaultsContext> defaults;

    public CommonJobContext(String jobId, JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        // name = JobNameContext.create(root.get("name")).orElse(jobId);
        // needs = NeedsContext.create(root.get("needs"));
        // permissions = PermissionsContext.create(root.get("permissions"), Set.of());
        // concurrency = ConcurrencyContext.create(root.get("concurrency"));
        // defaults = DefaultsContext.create(root.get("defaults"));
        // _if = IfContext.create(root.get("if"));
    }
}
