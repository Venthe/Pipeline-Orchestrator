package eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.common;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.experimental.UtilityClass;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

@UtilityClass
public class UrlContext {
    public static URL ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, ContextUtilities.fromTextMapper(UrlContext::getUrl));
    }

    public static Optional<URL> create(final JsonNode root) {
        return ContextUtilities.create(root, ContextUtilities.fromTextMapper(UrlContext::getUrl));
    }

    private static URL getUrl(String value) {
        try {
            return new URI(value).toURL();
        } catch (URISyntaxException | MalformedURLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
