package eu.venthe.pipeline.orchestrator.organizations.domain.plugins.gerrit;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;
import org.eclipse.jgit.transport.URIish;

import java.io.File;
import java.net.URISyntaxException;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
public class GitUtilities {
    public static void onRepository(String remoteRepositoryUrl, String refspec, Consumer<File> action) {
        onRepository(remoteRepositoryUrl, refspec, repository -> {
            action.accept(repository);
            return null;
        });
    }

    @SneakyThrows
    public static <T> T onRepository(String remoteRepositoryUrl, String refspec, Function<File, T> action) {
        try (AutoClosableFile tempDirectoryWrapper = createTempDirectory()) {
            File workingDirectory = tempDirectoryWrapper.directory();

            try (var git = initCommand(remoteRepositoryUrl, workingDirectory).call();
                 var repository = openRepository(workingDirectory)) {
                URIish remote = setupRemote(git, remoteRepositoryUrl);
                fetchRef(git, remote, refspec);
                checkoutFetchHead(git);

                return action.apply(repository.getDirectory());
            }
        }
    }

    private static void checkoutFetchHead(Git git) throws GitAPIException {
        git.checkout().setName("FETCH_HEAD").call();
    }

    private static void fetchRef(Git git, URIish remote, String refspec) throws GitAPIException {
        git.fetch()
                .setRemote(remote.toString())
                .setTransportConfigCallback(GitUtilities::setBasicAuth)
                .setDepth(1)
                .setRefSpecs(refspec)
                .call();
    }

    private static URIish setupRemote(Git git, String remoteRepositoryUrl) throws GitAPIException, URISyntaxException {
        String remote = "origin";
        URIish uri = new URIish(remoteRepositoryUrl);
        git.remoteAdd().setUri(uri).setName(remote).call();
        return uri;
    }

    @SneakyThrows
    private static InitCommand initCommand(String remoteRepositoryUrl, File tempDirectory) {
        return Git.init().setDirectory(tempDirectory);
    }

    /*private static LsRemoteCommand getLsRemoteCommand(Git git) {
        return git.lsRemote()
                .setTransportConfigCallback(GitUtilities::setBasicAuth);
    }*/

    private static void setBasicAuth(Transport transport) {
        if (transport instanceof TransportHttp) {
            ((TransportHttp) transport).setPreemptiveBasicAuthentication("admin", "secret");
        }
    }

    private static Repository openRepository(File repositoryDirectory) throws Exception {
        return new RepositoryBuilder()
                .setGitDir(repositoryDirectory)
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
