package eu.venthe.platform.projects.plugin.filesystem;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.plugin.template.ProjectRetrievalException;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.git.GitUtilities;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.URIish;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static eu.venthe.platform.projects.plugin.filesystem.FilesystemRepositorySourcePlugin.SOURCE_TYPE;

@Slf4j
public class FilesystemRepositorySourcePluginInstance implements RepositorySourcePluginInstance {
    private final Path rootPath;
    private final int maxDepth;

    @Override
    public String getSourceType() {
        return SOURCE_TYPE;
    }

    public FilesystemRepositorySourcePluginInstance(Path rootPath, int maxDepth) {
        this.rootPath = rootPath;

        if (maxDepth != 1) {
            // TODO: Handle nested projects
            //  Currently, raising max depth would result in incorrectly mapped project sourceConfigurationInternalIdentifier
            throw new IllegalArgumentException();
        }

        this.maxDepth = maxDepth;
    }

    @Override
    public Set<Repository> getAllRepositories() {
        try {
            Set<Path> results = new HashSet<>();
            Files.walkFileTree(rootPath, EnumSet.noneOf(FileVisitOption.class), maxDepth + 1, new RecursiveGitDirectoryFinder(results));
            return results.stream()
                    // TODO: Add mapping for nested projects
                    .map(this::getRepository)
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            log.error("Cannot retrieve repositories", exception);
            throw new ProjectRetrievalException(exception);
        }
    }

    @Override
    public Optional<Repository> getRepository(ProjectCorrelationId projectCorrelationId) {
        try {
            var repositoryPath = rootPath.resolve(projectCorrelationId.value());
            if (!Files.exists(repositoryPath)) {
                return Optional.empty();
            }

            if (!Files.exists(repositoryPath.resolve(".git"))) {
                log.debug(".git directory not found in {}", repositoryPath);
                throw new ProjectRetrievalException();
            }

            return Optional.of(getRepository(repositoryPath));
        } catch (Exception exception) {
            log.error("Cannot retrieve repositories", exception);
            throw new ProjectRetrievalException(exception);
        }
    }

    private Repository getRepository(Path repositoryPath) {
        var ref = getHeadRef(repositoryPath);
        var relativePath = rootPath.relativize(repositoryPath);
        var correlationId = FilesystemRepositorySourcePluginInstance.mapDirectoryNameToCorrelationId(relativePath.toString());
        return new Repository(correlationId, ref.getName(), getCommitHash(ref));
    }

    private static Ref getHeadRef(Path repositoryPath) {
        return GitUtilities.emptyRepository(git -> {
            try {
                git.remoteAdd()
                        .setName(GitUtilities.DEFAULT_REMOTE_NAME)
                        .setUri(toUri(repositoryPath))
                        .call();

                // TODO: Handle detached heads
                var heads = git.lsRemote().setRemote(GitUtilities.DEFAULT_REMOTE_NAME).call().stream()
                        .filter(e -> e.getName().equals("HEAD"))
                        .collect(Collectors.toSet());

                return heads.stream()
                        .map(Ref::getTarget)
                        .collect(MoreCollectors.onlyElement());
            } catch (GitAPIException | IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static String getCommitHash(Ref ref) {
        StringBuilder sb = new StringBuilder();
        char[] tmp = new char[40];
        ref.getTarget().getObjectId().copyTo(tmp, sb);
        return sb.toString();
    }

    private static URIish toUri(Path first) throws MalformedURLException {
        return new URIish(first.toAbsolutePath().toUri().toURL());
    }

    private static ProjectCorrelationId mapDirectoryNameToCorrelationId(String dir) {
        return new ProjectCorrelationId(dir.replace(" ", "-"));
    }
}
