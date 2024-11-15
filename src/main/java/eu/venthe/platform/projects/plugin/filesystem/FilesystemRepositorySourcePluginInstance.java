package eu.venthe.platform.projects.plugin.filesystem;

import eu.venthe.platform.projects.plugin.template.ProjectRetrievalException;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
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
            //  Currently, raising max depth would result in incorrectly mapped project name
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
                    .map(rootPath::relativize)
                    .map(Object::toString)
                    .map(FilesystemRepositorySourcePluginInstance::mapDirectoryNameToRepositoryName)
                    // TODO: Add mapping for nested projects
                    // TODO: Add tracked branch information
                    .map((String repositoryName) -> new Repository(repositoryName, null, null))
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            log.error("Cannot retrieve repositories", exception);
            throw new ProjectRetrievalException(exception);
        }
    }

    @Override
    public Optional<Repository> getRepository(String repositoryName) {
        try {
            var repositoryPath = rootPath.resolve(repositoryName);
            if (!Files.exists(repositoryPath)) {
                return Optional.empty();
            }

            if (!Files.exists(repositoryPath.resolve(".git"))) {
                log.debug(".git directory not found in {}", repositoryPath);
                throw new ProjectRetrievalException();
            }
            var relativeRepositoryDirectory = rootPath.relativize(repositoryPath);
            var mappedRepositoryName = FilesystemRepositorySourcePluginInstance.mapDirectoryNameToRepositoryName(relativeRepositoryDirectory.toString());
            // TODO: Add tracked branch information
            return Optional.of(new Repository(mappedRepositoryName, null, null));
        } catch (Exception exception) {
            log.error("Cannot retrieve repositories", exception);
            throw new ProjectRetrievalException(exception);
        }
    }

    private static String mapDirectoryNameToRepositoryName(String dir) {
        return dir.replace(" ", "-");
    }
}
