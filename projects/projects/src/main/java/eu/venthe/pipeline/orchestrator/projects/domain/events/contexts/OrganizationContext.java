package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions.Organization;
import eu.venthe.pipeline.orchestrator._archive2.utilities.ContextUtilities;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Deprecated
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrganizationContext implements Organization {
    private final ObjectNode root;

    public static Optional<Organization> create(JsonNode root) {
        return ContextUtilities.get(OrganizationContext::new, root);
    }
}
