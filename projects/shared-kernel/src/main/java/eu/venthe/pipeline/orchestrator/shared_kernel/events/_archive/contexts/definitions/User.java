package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.definitions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.Optional;

public interface User {
    String getLogin();

    // default Integer getId() {
    //     throw new UnsupportedOperationException();
    // }

    // default String getNodeId() {
    //     throw new UnsupportedOperationException();
    // }

    Optional<String> getName();

    Optional<String> getEmail();

    // default URI getAvatarUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default String getGravatarId() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getHtmlUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getFollowersUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default UriTemplate getFollowingUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default UriTemplate getGistsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default UriTemplate getStarredUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getSubscriptionsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getOrganizationsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getReposUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default UriTemplate getEventsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getReceivedEventsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default Type getType() {
    //     throw new UnsupportedOperationException();
    // }

    // default Boolean getSiteAdmin() {
    //     throw new UnsupportedOperationException();
    // }

    @RequiredArgsConstructor
    enum Type {
        BOT("Bot"),
        USER("User"),
        ORGANIZATION("Organization");

        private final String value;
    }
}
