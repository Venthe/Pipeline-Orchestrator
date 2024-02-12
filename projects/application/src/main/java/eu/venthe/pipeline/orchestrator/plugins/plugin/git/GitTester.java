package eu.venthe.pipeline.orchestrator.plugins.plugin.git;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;

import java.io.File;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class GitTester {

    public static final String CONFIG_REF = "refs/meta/config";

    public static void cloneRemoteRepository(String remoteRepositoryUrl) throws Exception {
        try (AutoClosableFile tempDirectoryWrapper = createTempDirectory()) {
            File workingDirectory = tempDirectoryWrapper.directory();

            // Execute the clone command
            try (var git = cloneCommand(remoteRepositoryUrl, workingDirectory).call();
                 var repository = openRepository(workingDirectory)) {
                Collection<Ref> refs = getLsRemoteCommand(git)
                        .call();
                git.fetch()
                        .setTransportConfigCallback(GitTester::setBasicAuth)
                        .setRefSpecs(CONFIG_REF)
                        .call();
                git.checkout().setName("FETCH_HEAD").call();
//                log.info(Stream.of(repository.getDirectory().listFiles()).map(File::getName).collect(Collectors.joining("\n")));

                File mantleConfiguration = new File(workingDirectory, "mantle.yml");
            }
        }
    }

    private static CloneCommand cloneCommand(String remoteRepositoryUrl, File tempDirectory) {
        return Git.cloneRepository()
                .setTransportConfigCallback(GitTester::setBasicAuth)
                .setURI(remoteRepositoryUrl)
                .setDirectory(tempDirectory);
    }

    private static LsRemoteCommand getLsRemoteCommand(Git git) {
        return git.lsRemote()
                .setTransportConfigCallback(GitTester::setBasicAuth);
    }

    private static void setBasicAuth(Transport transport) {
        if (transport instanceof TransportHttp) {
            ((TransportHttp) transport).setPreemptiveBasicAuthentication("admin", "secret");
        }
    }

    private static Repository openRepository(File repositoryDirectory) throws Exception {
        return new RepositoryBuilder().setGitDir(repositoryDirectory)
                .readEnvironment()
                .findGitDir()
                .build();
    }

    private static AutoClosableFile createTempDirectory() throws Exception {
        // Create a temporary directory
        File tempDirectory = File.createTempFile("jgit-temp-clone", Long.toString(System.nanoTime()));
        if (!tempDirectory.delete() || !tempDirectory.mkdir()) {
            throw new Exception("Failed to create temp directory");
        }
        return new AutoClosableFile(tempDirectory);
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
