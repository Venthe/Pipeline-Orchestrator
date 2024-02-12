package eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit.utils.GerritHeaders;
import eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit.utils.GerritHookMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.UUID.randomUUID;
import static org.springframework.http.HttpMethod.GET;

@Component
@RequiredArgsConstructor
@Getter
public class GerritApi {
    private final GerritHeaders gerritHeaders;
    private final GerritUrls gerritUrls;
    private final RestTemplate restTemplate;
    private final GerritHookMapper hookMapper;

    private final Changes changes = new Changes();
    private final Projects projects = new Projects();
    private final Config Config = new Config();

    private Supplier<ResponseEntity<String>> call(String url, HttpEntity<?> entity) {
        URI uriComponentsBuilder = UriComponentsBuilder.fromUriString(url).build(true).toUri();
        return call(uriComponentsBuilder, entity);
    }

    private Supplier<ResponseEntity<String>> call(URI url, HttpEntity<?> entity) {
        return () -> restTemplate.exchange(url, GET, entity, String.class);
    }

    @SneakyThrows
    private JsonNode map(Supplier<ResponseEntity<String>> supplier) {
        String body = supplier.get().getBody();
        return hookMapper.mapToNodes(body);
    }

    public HttpEntity<?> generateTraceId() {
        return getHeaders(gerritHeaders.prepareHeaders(getTraceId()));
    }

    private static String getTraceId() {
        return randomUUID().toString();
    }

    private static HttpEntity<?> getHeaders(HttpHeaders httpHeaders1) {
        return new HttpEntity<>(httpHeaders1);
    }


    public class Config {
        public JsonNode getConfig(HttpEntity<?> traceId) {
            return map(call(gerritUrls.getConfig().getServerInfo(), traceId));
        }
    }

    public class Changes {
        public JsonNode getAllRevisions(String changeId, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getChanges().getAllRevisions(changeId), traceId));
        }

        public JsonNode getCommitFilesForRevision(String changeId, String revisionId, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getChanges().filesForRevision(changeId, revisionId), traceId));
        }

        public JsonNode getCommitForRevision(String changeId, String revisionId, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getChanges().commitForRevision(changeId, revisionId), traceId));
        }
    }

    public class Projects {
        public JsonNode getProjects(HttpEntity<?> traceId) {
            return map(call(gerritUrls.getProjects().getProjects(), traceId));
        }

        public Optional<byte[]> getFileForBranch(String projectName, String branch, String path, HttpEntity<?> traceId) {
            ResponseEntity<String> stringResponseEntity = call(gerritUrls.getProjects().getFileForBranch(projectName, branch, path), traceId).get();
            if (stringResponseEntity.getStatusCode().is4xxClientError()) {
                return Optional.empty();
            }

            if (stringResponseEntity.getStatusCode().isError()) {
                throw new RuntimeException();
            }

            return Optional.of(Base64.getDecoder().decode(stringResponseEntity.getBody()));
        }

        public JsonNode getProject(String projectId, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getProjects().getProject(projectId), traceId));
        }

        public JsonNode getCommitForProject(String projectId, String revision, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getProjects().getCommitForProject(projectId, revision), traceId));
        }

        public String getRevisionForBranchOrRevision(String projectId, String revision, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getProjects().getRevisionForBranchOrRevision(projectId, revision), traceId)).get("revision").asText();
        }

        public JsonNode getCommitFilesForProject(String projectName, String revision, HttpEntity<?> traceId) {
            return map(call(gerritUrls.getProjects().getCommitFilesForProject(projectName, revision), traceId));
        }
    }
}
