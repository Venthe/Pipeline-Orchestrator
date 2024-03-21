package eu.venthe.pipeline.orchestrator.projects.domain.events.contexts.definitions;

import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Optional;

public interface Organization {
    default String getLogin() {
        throw new UnsupportedOperationException();
    }

    default Integer getId() {
        throw new UnsupportedOperationException();
    }

    default String getNodeId() {
        throw new UnsupportedOperationException();
    }

    default URI getUrl() {
        throw new UnsupportedOperationException();
    }

    default Optional<URI> getHtmlUrl() {
        throw new UnsupportedOperationException();
    }

    default URI getReposUrl() {
        throw new UnsupportedOperationException();
    }

    default URI getEventsUrl() {
        throw new UnsupportedOperationException();
    }

    default URI getHooksUrl() {
        throw new UnsupportedOperationException();
    }

    default URI getIssuesUrl() {
        throw new UnsupportedOperationException();
    }

    default UriTemplate getMembersUrl() {
        throw new UnsupportedOperationException();
    }

    default UriTemplate getPublicMembersUrl() {
        throw new UnsupportedOperationException();
    }

    default URI getAvatarUrl() {
        throw new UnsupportedOperationException();
    }

    default Optional<String> getDescription() {
        throw new UnsupportedOperationException();
    }

}
