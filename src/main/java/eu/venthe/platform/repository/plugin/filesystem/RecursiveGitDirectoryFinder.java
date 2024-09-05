package eu.venthe.platform.repository.plugin.filesystem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

@Slf4j
@Getter
@RequiredArgsConstructor
public class RecursiveGitDirectoryFinder extends SimpleFileVisitor<Path> {
    private final Set<Path> results;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes) {
        if (!Files.exists(dir.resolve(".git"))) {
            log.debug(".git directory not found in {}", dir);
            return FileVisitResult.CONTINUE;
        }

        log.debug(".git directory found in {}", dir);
        results.add(dir);
        return FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exception) {
        log.warn("Failed to visit a path {}", file, exception);
        return FileVisitResult.CONTINUE;
    }
}
