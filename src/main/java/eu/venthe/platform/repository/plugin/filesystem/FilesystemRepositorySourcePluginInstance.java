package eu.venthe.platform.repository.plugin.filesystem;

import eu.venthe.platform.repository.plugin.template.Repository;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static eu.venthe.platform.repository.plugin.filesystem.FilesystemRepositorySourcePlugin.SOURCE_TYPE;

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
                    .map(Repository::new)
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            log.error("Cannot retrieve repositories", exception);
            throw new ProjectRetrievalException();
        }
    }

    private static String mapDirectoryNameToRepositoryName(String dir) {
        return dir.replace(" ", "-");
    }
}
