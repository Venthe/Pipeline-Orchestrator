package eu.venthe.platform.source_configuration.domain.plugins.gerrit;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.gerrit.api.ProjectsApi;
import eu.venthe.platform.gerrit.model.ProjectInfo;
import eu.venthe.platform.shared_kernel.io.File;
import eu.venthe.platform.shared_kernel.io.Metadata;
import eu.venthe.platform.shared_kernel.project.ProjectStatus;
import eu.venthe.platform.source_configuration.domain.plugins.template.Project;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import eu.venthe.platform.source_configuration.domain.model.Revision;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import eu.venthe.platform.source_configuration.domain.plugins.template.ProjectSourcePluginInstance;
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
import static eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritProjectSourcePlugin.SOURCE_TYPE;

@Slf4j
public class GerritPluginInstance implements ProjectSourcePluginInstance {
    @Getter
    private final SourceType sourceType;
    private final ProjectsApi projectsApi;
    private final GerritConfiguration configuration;

    public GerritPluginInstance(GerritConfiguration configuration) {
        this.configuration = configuration;
        var apiClient = new GerritApiClient(configuration);
        this.sourceType = SOURCE_TYPE;
        projectsApi = new ProjectsApi(apiClient);
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
                        "project-created",
                        "patchset-created",
                        "ref-updated",
                        "reviewer-added",
                        "reviewer-deleted",
                        "topic-changed",
                        "wip-state-changed",
                        "private-state-changed",
                        "vote-deleted",
                        "project-head-updated"
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
    public Optional<File> getFile(SourceProjectId sourceProjectId, Revision revision, Path path) {
        String string = UriBuilder.fromUri(configuration.getBasePath()).path("/a/").path(sourceProjectId.value()).toString();
        return GitUtilities.onRepository(string, revision, rootDirectory -> {
            // FIXME
            return Optional.of(rootDirectory.toPath().resolve(path)).map(Path::toFile).filter(java.io.File::exists).map(GerritPluginInstance::getBytes).map(e -> null);
        });
    }

    @SneakyThrows
    private static byte[] getBytes(java.io.File e) {
        return Files.readAllBytes(e.toPath());
    }

    @Override
    public Collection<Metadata> getFileList(SourceProjectId sourceProjectId, Revision revision, Path path) {
        String string = UriBuilder.fromUri(configuration.getBasePath()).path("/a/").path(sourceProjectId.value()).toString();
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
    public Stream<Project> getProjects() {
        return getListProjects().entrySet().stream()
                .filter(project -> !Objects.equals(project.getValue().getState(), ProjectInfo.StateEnum.HIDDEN))
                .map(project -> new Project(new SourceProjectId(project.getKey()), statusMapper(project.getValue().getState())))
                .distinct();
    }

    @Override
    public Stream<SourceProjectId> getProjectIdentifiers() {
        return getProjects().map(Project::sourceProjectId);
    }

    @Override
    public Optional<Project> getProject(SourceProjectId id) {
        // FIXME: Don't ask for all projects
        return getProjects()
                .filter(p -> p.sourceProjectId().equals(id))
                .collect(MoreCollectors.toOptional());
    }

    private Map<String, ProjectInfo> getListProjects() {
        return projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, getTraceId());
    }

    private static ProjectStatus statusMapper(ProjectInfo.StateEnum stateEnum) {
        return switch (stateEnum) {
            case ACTIVE -> ProjectStatus.ACTIVE;
            case READ_ONLY -> ProjectStatus.ARCHIVED;
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
