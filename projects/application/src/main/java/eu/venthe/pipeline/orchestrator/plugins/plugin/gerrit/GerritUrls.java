package eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.plugins.plugin.gerrit.config.GerritBindingConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class GerritUrls {
    private final GerritBindingConfiguration configuration;
    private final Changes changes = new Changes();
    private final Projects projects = new Projects();
    private final Config Config = new Config();

    private static String sanitize(String value) {
        return value.replaceAll("/", "%2F");
    }

    private String getUrl(String url) {
        return "%s/a/%s".formatted(configuration.getUrl(), url);
    }

    public class Config {
        public String getServerInfo() {
            return getUrl("config/server/info");
        }
    }

    public class Changes {
        public String getAllRevisions(String changeId) {
            return getUrl("changes/%s?o=ALL_REVISIONS".formatted(changeId));
        }

        public String filesForRevision(String changeId, String revisionId) {
            return getUrl("changes/%s/revisions/%s/files".formatted(changeId, revisionId));
        }

        public String commitForRevision(String changeId, String revisionId) {
            return getUrl("changes/%s/revisions/%s/commit".formatted(changeId, revisionId));
        }
    }

    public class Projects {
        public String getProjects() {
            return getUrl("projects/?d&t&type=CODE");
        }

        public String getProject(String projectId) {
            return getUrl("projects/%s").formatted(projectId);
        }

        public String getCommitForProject(String project, String revisionId) {
            return getUrl("projects/%s/commits/%s".formatted(project, revisionId));
        }

        public String getRevisionForBranchOrRevision(String projectId, String revision) {
            return getUrl("projects/%s/branches/%s".formatted(projectId, revision));
        }

        public String getFileForBranch(String projectName, String branch, String path) {
            return getUrl("projects/%s/branches/%s/files/%s/content".formatted(projectName, sanitize(branch), sanitize(path)));
        }

        public String getCommitFilesForProject(String project, String revision) {
            return getUrl("projects/%s/commits/%s/files".formatted(project, revision));
        }
    }
}
