package eu.venthe.pipeline.orchestrator.plugins.gerrit;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.plugins.gerrit.config.GerritBindingConfiguration;
import eu.venthe.pipeline.orchestrator.plugins.gerrit.utils.GerritHeaders;
import eu.venthe.pipeline.orchestrator.plugins.gerrit.utils.GerritResponseMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
@Getter
public class GerritApi {
    private final GerritHeaders gerritHeaders;
    private final RestTemplate restTemplate;
    private final GerritResponseMapper gerritResponseMapper;

    private final Changes changes = new Changes();
    private final Projects projects = new Projects();
    private final Config Config = new Config();

    private static String sanitize(String value) {
        return value.replaceAll("/", "%2F");
    }

    private ResponseEntity<String> call(String url, HttpEntity<?> entity) {
        URI uriComponentsBuilder = UriComponentsBuilder.fromUriString(url).build(true).toUri();
        return call(uriComponentsBuilder, entity);
    }

    private ResponseEntity<String> call(URI url, HttpEntity<?> entity) {
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    @SneakyThrows
    private JsonNode mapToJsonNode(ResponseEntity<String> supplier) {
        return gerritResponseMapper.mapToJsonNode(supplier.getBody());
    }

    public class Config {
        public JsonNode getConfig(GerritBindingConfiguration configuration, HttpEntity<?> entity) {
            return mapToJsonNode(call(authorizedUrl(configuration, "config/server/info"), entity));
        }
    }

    public class Changes {
        public JsonNode getAllRevisions(GerritBindingConfiguration configuration, String changeId, HttpEntity<?> entity) {
            String url = "changes/%s?o=ALL_REVISIONS".formatted(changeId);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity));
        }

        public JsonNode getCommitFilesForRevision(GerritBindingConfiguration configuration, String changeId, String revisionId, HttpEntity<?> entity) {
            String url = "changes/%s/revisions/%s/files".formatted(changeId, revisionId);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity));
        }

        public JsonNode getCommitForRevision(GerritBindingConfiguration configuration, String changeId, String revisionId, HttpEntity<?> entity) {
            String url = "changes/%s/revisions/%s/commit".formatted(changeId, revisionId);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity));
        }
    }

    public class Projects {
        public JsonNode getProjects(GerritBindingConfiguration configuration, HttpEntity<?> entity) {
            return mapToJsonNode(call(authorizedUrl(configuration, "projects/?d&t&type=CODE"), entity));
        }

        public Optional<byte[]> getFileForBranch(GerritBindingConfiguration configuration, String projectName, String branch, String path, HttpEntity<?> entity) {
            String url = "projects/%s/branches/%s/files/%s/content".formatted(projectName, sanitize(branch), sanitize(path));
            ResponseEntity<String> stringResponseEntity = call(authorizedUrl(configuration, url), entity);
            if (stringResponseEntity.getStatusCode().is4xxClientError()) {
                return Optional.empty();
            }

            if (stringResponseEntity.getStatusCode().isError()) {
                throw new RuntimeException();
            }

            return Optional.of(Base64.getDecoder().decode(stringResponseEntity.getBody()));
        }

        public JsonNode getProject(GerritBindingConfiguration configuration, String projectId, HttpEntity<?> entity) {
            String url = "projects/%s";
            return mapToJsonNode(call(authorizedUrl(configuration, url).formatted(projectId), entity));
        }

        public JsonNode getCommitForProject(GerritBindingConfiguration configuration, String projectId, String revision, HttpEntity<?> entity) {
            String url = "projects/%s/commits/%s".formatted(projectId, revision);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity));
        }

        public String getRevisionForBranchOrRevision(GerritBindingConfiguration configuration, String projectId, String revision, HttpEntity<?> entity) {
            String url = "projects/%s/branches/%s".formatted(projectId, revision);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity)).get("revision").asText();
        }

        public JsonNode getCommitFilesForProject(GerritBindingConfiguration configuration, String projectName, String revision, HttpEntity<?> entity) {
            String url = "projects/%s/commits/%s/files".formatted(projectName, revision);
            return mapToJsonNode(call(authorizedUrl(configuration, url), entity));
        }
    }

    private static String authorizedUrl(GerritBindingConfiguration configuration, String path) {
        return "%s/a/%s".formatted(configuration.getUrl(), path);
    }
}
