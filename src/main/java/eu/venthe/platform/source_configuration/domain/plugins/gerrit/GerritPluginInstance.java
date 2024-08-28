package eu.venthe.platform.source_configuration.domain.plugins.gerrit;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.gerrit.api.RepositoryApi;
import eu.venthe.platform.gerrit.model.RepositoryInfo;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.shared_kernel.project.RepositoryStatus;
import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryName;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePluginInstance;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritHeaders.getTraceId;
import static eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritRepositorySourcePlugin.SOURCE_TYPE;

@Slf4j
public class GerritPluginInstance implements RepositorySourcePluginInstance {
    @Getter
    private final SourceType sourceType;
    private final RepositoryApi repositorysApi;
    private final GerritConfiguration configuration;

    public GerritPluginInstance(GerritConfiguration configuration) {
        this.configuration = configuration;
        var apiClient = new GerritApiClient(configuration);
        this.sourceType = SOURCE_TYPE;
        repositorysApi = new RepositoryApi(apiClient);
    }

   /* @SneakyThrows
    @Override
    public Collection<Mapping> provideEndpointMappings() {
        GerritWebhook gerritWebhook = new GerritWebhook();
        Method method = gerritWebhook.getClass().getMethod("handle", String.class);
        return Stream.of(
                        "assignee-changed",
                        "change-abandoned",
                        "change-deleted",
                        "change-merged",
                        "change-restored",
                        "comment-added",
                        "dropped-output",
                        "repository-created",
                        "patchset-created",
                        "ref-updated",
                        "reviewer-added",
                        "reviewer-deleted",
                        "topic-changed",
                        "wip-state-changed",
                        "private-state-changed",
                        "vote-deleted",
                        "repository-head-updated"
                )
                .map(a -> String.join("/", "/handle", a))
                .map(GerritPluginInstance::mappingInfoFor)
                .map(requestMappingInfo -> new Mapping(gerritWebhook, method, requestMappingInfo))
                .toList();
    }

    private static RequestMappingInfo mappingInfoFor(String s) {
        return RequestMappingInfo.paths(s)
                .methods(RequestMethod.POST)
                .consumes("application/json")
                .build();
    }*/

    @Override
    public Optional<File> getFile(SourceRepositoryName sourceRepositoryName, RevisionShortName simpleRevision, Path path) {
        String string = UriBuilder.fromUri(configuration.getBasePath()).path("/a/").path(sourceRepositoryName.value()).toString();
        return GitUtilities.onRepository(string, simpleRevision, rootDirectory -> {
            // FIXME
            return Optional.of(rootDirectory.toPath().resolve(path)).map(Path::toFile).filter(java.io.File::exists).map(GerritPluginInstance::getBytes).map(e -> null);
        });
    }

    @SneakyThrows
    private static byte[] getBytes(java.io.File e) {
        return Files.readAllBytes(e.toPath());
    }

    @Override
    public Collection<Metadata> getFileList(SourceRepositoryName sourceRepositoryName, RevisionShortName simpleRevision, Path path) {
        String string = UriBuilder.fromUri(configuration.getBasePath()).path("/a/").path(sourceRepositoryName.value()).toString();
        throw new UnsupportedOperationException();
        // return GitUtilities.onRepository(string, revision, rootDirectory -> {
        //     java.io.File relativeDirectory = rootDirectory.toPath().resolve(path).toFile();
        //     java.io.File[] files = Optional.ofNullable(relativeDirectory.listFiles()).orElseThrow();
        //     return Stream.of(files)
        //             .map(toFileDto(rootDirectory.toPath()))
        //             .collect(Collectors.toSet());
        // });
    }

    private static Function<java.io.File, File> toFileDto(Path path) {
        return f -> {
            try {
                Path path1 = f.toPath();
                // return new File(path.relativize(path1), Files.size(path1), f.isFile() ? FileDto.Type.FILE : FileDto.Type.DIRECTORY);
                throw new UnsupportedOperationException();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Stream<Repository> getRepository() {
        return getListRepository().entrySet().stream()
                .filter(repository -> !Objects.equals(repository.getValue().getState(), RepositoryInfo.StateEnum.HIDDEN))
                .map(repository -> new Repository(new SourceRepositoryName(repository.getKey()), statusMapper(repository.getValue().getState())))
                .distinct();
    }

    @Override
    public Stream<SourceRepositoryName> getRepositoryNameentifiers() {
        return getRepository().map(Repository::sourceRepositoryName);
    }

    @Override
    public Optional<Repository> getRepository(SourceRepositoryName id) {
        // FIXME: Don't ask for all repositorys
        return getRepository()
                .filter(p -> p.sourceRepositoryName().equals(id))
                .collect(MoreCollectors.toOptional());
    }

    private Map<String, RepositoryInfo> getListRepository() {
        return repositorysApi.listRepository(null, null, null, null, null, null, null, null, null, null, null, null, null, null, getTraceId());
    }

    private static RepositoryStatus statusMapper(RepositoryInfo.StateEnum stateEnum) {
        return switch (stateEnum) {
            case ACTIVE -> RepositoryStatus.ACTIVE;
            case READ_ONLY -> RepositoryStatus.ARCHIVED;
            case null, default -> throw new UnsupportedOperationException();
        };
    }
}


//package eu.venthe.pipeline.orchestrator.infrastructure.git;
//
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.jgit.api.CloneCommand;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.LsRemoteCommand;
//import org.eclipse.jgit.lib.Ref;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.lib.RepositoryBuilder;
//import org.eclipse.jgit.transport.Transport;
//import org.eclipse.jgit.transport.TransportHttp;
//
//import java.io.File;
//import java.util.Collection;
//
//@Slf4j
//public class GitTester {
//
//    public static final String CONFIG_REF = "refs/meta/config";
//
//    public static void cloneRemoteRepository(String remoteRepositoryUrl) throws Exception {
//        try (AutoClosableFile tempDirectoryWrapper = createTempDirectory()) {
//            File workingDirectory = tempDirectoryWrapper.directory();
//
//            // Execute the clone command
//            try (var git = cloneCommand(remoteRepositoryUrl, workingDirectory).call();
//                 var repository = openRepository(workingDirectory)) {
//                Collection<Ref> refs = getLsRemoteCommand(git)
//                        .call();
//                git.fetch()
//                        .setTransportConfigCallback(GitTester::setBasicAuth)
//                        .setRefSpecs(CONFIG_REF)
//                        .call();
//                git.checkout().setName("FETCH_HEAD").call();
////                log.info(Stream.of(repository.getDirectory().listFiles()).map(File::getName).collect(Collectors.joining("\n")));
//
//                File mantleConfiguration = new File(workingDirectory, "mantle.yml");
//            }
//        }
//    }
//
//    private static CloneCommand cloneCommand(String remoteRepositoryUrl, File tempDirectory) {
//        return Git.cloneRepository()
//                .setTransportConfigCallback(GitTester::setBasicAuth)
//                .setURI(remoteRepositoryUrl)
//                .setDirectory(tempDirectory);
//    }
//
//    private static LsRemoteCommand getLsRemoteCommand(Git git) {
//        return git.lsRemote()
//                .setTransportConfigCallback(GitTester::setBasicAuth);
//    }
//
//    private static void setBasicAuth(Transport transport) {
//        if (transport instanceof TransportHttp) {
//            ((TransportHttp) transport).setPreemptiveBasicAuthentication("admin", "secret");
//        }
//    }
//
//    private static Repository openRepository(File repositoryDirectory) throws Exception {
//        return new RepositoryBuilder().setGitDir(repositoryDirectory)
//                .readEnvironment()
//                .findGitDir()
//                .build();
//    }
//
//    private static AutoClosableFile createTempDirectory() throws Exception {
//        // Create a temporary directory
//        File tempDirectory = File.createTempFile("jgit-temp-clone", Long.toString(System.nanoTime()));
//        if (!tempDirectory.delete() || !tempDirectory.mkdir()) {
//            throw new Exception("Failed to create temp directory");
//        }
//        return new AutoClosableFile(tempDirectory);
//    }
//
//    private record AutoClosableFile(File directory) implements AutoCloseable {
//        @SuppressWarnings("ResultOfMethodCallIgnored")
//        @Override
//        public void close() throws Exception {
//            deleteDirectory(directory);
//            directory.delete();
//        }
//
//        private void deleteDirectory(File directory) throws Exception {
//            // Recursively delete the temporary directory
//            File[] files = directory.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    deleteDirectory(file);
//                }
//            }
//            if (!directory.delete()) {
//                throw new Exception("Failed to delete temp directory");
//            }
//        }
//    }
//}
