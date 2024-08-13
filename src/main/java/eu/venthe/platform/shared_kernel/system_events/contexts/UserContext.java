package eu.venthe.platform.shared_kernel.system_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.EmailContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.common.UrlContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.model.UserTypeContext;
import eu.venthe.platform.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import eu.venthe.platform.shared_kernel.system_events.model.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.net.URL;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Getter
public class UserContext {
    private final String id;

    @Builder
    private UserContext(final @NonNull String id, final @NonNull String login, final @NonNull UserType userType, final String email, final String name, final URL url) {
        this.id = id;
        this.login = login;
        this.email = Optional.ofNullable(email);
        this.name = Optional.ofNullable(name);
        this.url = Optional.ofNullable(url);
        this.userType = userType;
    }

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
