package eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.EmailContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.common.UrlContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.model.UserTypeContext;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.contexts.utilities.ContextUtilities;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.model.UserType;
import lombok.Getter;

import java.net.URL;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
public class UserContext {
    private final String id;
    private final String login;
    private final Optional<String> email;
    private final Optional<String> name;
    private final Optional<URL> url;
    private final UserType userType;

    private UserContext(final JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        id = ContextUtilities.Text.ensure(root.get("id"));
        login = ContextUtilities.Text.ensure(root.get("login"));
        email = EmailContext.create(root.get("email"));
        name = ContextUtilities.Text.create(root.get("name"));
        url = UrlContext.create(root.get("url"));
        userType = UserTypeContext.ensure(root.get("userType"));
    }

    public static UserContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, UserContext::new);
    }

    public static Optional<UserContext> create(final JsonNode root) {
        return ContextUtilities.create(root, UserContext::new);
    }
}
