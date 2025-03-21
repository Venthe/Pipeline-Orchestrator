package eu.venthe.platform.shared_kernel.git;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class GitUtilities {

    public static final String DEFAULT_REMOTE_NAME = "origin";
    public static final String FETCH_HEAD = "FETCH_HEAD";

    public static void emptyRepository(Consumer<Git> action) {
        emptyRepository(git -> {
            action.accept(git);
            return null;
        });
    }

    @SneakyThrows
    public static <T> T emptyRepository(Function<Git, T> action) {
        try (AutoClosableFile tempDirectoryWrapper = createTempDirectory()) {
            File workingDirectory = tempDirectoryWrapper.directory();

            try (var git = initTempRepository(workingDirectory).call()) {
                return action.apply(git);
            }
        }
    }

    private static AutoClosableFile createTempDirectory() throws Exception {
        // Create a temporary directory
        File tempDirectory = File.createTempFile("repository-temp-clone", Long.toString(System.nanoTime()));
        if (!tempDirectory.delete() || !tempDirectory.mkdir()) {
            throw new Exception("Failed to create temp directory");
        }
        return new AutoClosableFile(tempDirectory);
    }

    @SneakyThrows
    private static InitCommand initTempRepository(File tempDirectory) {
        return Git.init().setDirectory(tempDirectory);
    }

    private record AutoClosableFile(File directory) implements AutoCloseable {
        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Override
        public void close() throws Exception {
            deleteDirectory(directory);
            directory.delete();
        }

        private void deleteDirectory(File directory) throws Exception {
            // Recursively delete the temporary directory
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
            if (!directory.delete()) {
                throw new Exception("Failed to delete temp directory");
            }
        }
    }
}
