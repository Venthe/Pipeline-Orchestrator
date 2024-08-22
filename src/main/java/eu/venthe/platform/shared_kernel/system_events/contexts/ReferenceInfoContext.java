package eu.venthe.platform.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.git.SimpleRevision;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.GitHashContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.git.SimpleRevisionContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.Getter;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
public class ReferenceInfoContext {
    private final String label;
    private final SimpleRevision ref;
    private final Optional<RepositoryContext> repo;
    private final String sha;
    private final UserContext user;

    private ReferenceInfoContext(final JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        label = ContextUtilities.Text.ensure(root.get("label"));
        // TODO: Full revision?
        ref = SimpleRevisionContext.ensure(root.get("ref"));
        repo = RepositoryContext.create(root.get("repo"));
        sha = GitHashContext.ensure(root.get("sha"));
        user = UserContext.ensure(root.get("user"));
    }

    public static ReferenceInfoContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, ReferenceInfoContext::new);
    }
}
