/*
package eu.venthe.pipeline.orchestrator.plugins.gerrit._archive.utils;

import eu.venthe.pipeline.orchestrator.plugins.gerrit.config.GerritBindingConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.List.of;
import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class GerritHeaders {

    public HttpEntity<?> prepareHeaders(GerritBindingConfiguration configuration) {
        return prepareHeaders(configuration, getTraceId());
    }

    private HttpEntity<?> prepareHeaders(GerritBindingConfiguration configuration, String traceId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("X-Gerrit-Trace", of(traceId));
        httpHeaders.put("Authorization", of(getBasicAuth(configuration.getUsername() + ":" + configuration.getPassword())));
        return entityFromHeaders(httpHeaders);
    }

    private static String getTraceId() {
        return randomUUID().toString();
    }

    private static HttpEntity<?> entityFromHeaders(HttpHeaders httpHeaders1) {
        return new HttpEntity<>(httpHeaders1);
    }

    public static String getBasicAuth(String value) {
        return "Basic " + base64Encode(value);
    }

    private static String base64Encode(String value) {
        return Base64.getEncoder()
                .encodeToString(value.getBytes(UTF_8));
    }
}
*/
