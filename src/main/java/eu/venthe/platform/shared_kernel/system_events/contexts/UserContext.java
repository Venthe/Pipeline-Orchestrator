package eu.venthe.platform.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.EmailContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.model.UserTypeContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.UserType;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.net.URL;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
@Builder
public class UserContext {
    private final String id;
    private final String login;
    private final @Nullable String email;
    private final @Nullable String name;
    private final @Nullable URL url;
    private final UserType userType;

    private UserContext(final JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        id = ContextUtilities.Text.ensure(root.get("id"));
        login = ContextUtilities.Text.ensure(root.get("login"));
        email = EmailContext.create(root.get("email")).orElse(null);
        name = ContextUtilities.Text.create(root.get("name")).orElse(null);
        url = UrlContext.create(root.get("url")).orElse(null);
        userType = UserTypeContext.ensure(root.get("userType"));
    }

    public static UserContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, UserContext::new);
    }

    public static Optional<UserContext> create(final JsonNode root) {
        return ContextUtilities.create(root, UserContext::new);
    }
}
