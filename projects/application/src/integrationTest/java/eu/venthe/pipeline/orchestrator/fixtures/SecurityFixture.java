package eu.venthe.pipeline.orchestrator.fixtures;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dasniko.testcontainers.keycloak.KeycloakContainer;
import eu.venthe.pipeline.orchestrator.other.jwt.JsonWebToken;
import io.cucumber.spring.ScenarioScope;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Collections;

import static eu.venthe.pipeline.orchestrator.Constants.Keycloak.CLIENT;
import static eu.venthe.pipeline.orchestrator.Constants.Keycloak.REALM;


@Slf4j
@Component
@ScenarioScope
@RequiredArgsConstructor
public class SecurityFixture {
    private final KeycloakContainer container;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public JsonWebToken login(String username, String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", Collections.singletonList("password"));
        formData.put("client_id", Collections.singletonList(CLIENT));
        formData.put("username", Collections.singletonList(username));
        formData.put("password", Collections.singletonList(password));
        JsonNode result = restClient.post().uri(new URIBuilder(container.getAuthServerUrl() + "/realms/" + REALM + "/protocol/openid-connect/token").build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(JsonNode.class);
        return new eu.venthe.pipeline.orchestrator.other.jwt.JsonWebToken(objectMapper, result.get("access_token").asText());
    }

    @SneakyThrows
    public String getPublicKey() {
        return restClient.get().uri(new URIBuilder(container.getAuthServerUrl() + "/realms/" + REALM).build())
                 .retrieve()
                 .body(JsonNode.class)
                 .get("public_key")
                 .asText();
    }
}
