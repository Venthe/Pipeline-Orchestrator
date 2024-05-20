package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.model.ProjectInfo;
import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.FileDto;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;
import jakarta.ws.rs.core.UriBuilder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit.GerritHeaders.getTraceId;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Value
public class GerritPluginInstance implements ProjectSourcePlugin.PluginInstance {
    ProjectsApi projectsApi;
    GerritConfiguration configuration;

    public GerritPluginInstance(GerritConfiguration configuration) {
        this.configuration = configuration;
        var apiClient = new GerritApiClient(configuration);
        projectsApi = new ProjectsApi(apiClient);
    }

    @Override
    public Optional<byte[]> getFile(String projectIdentifier, String revision, Path path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<FileDto> getFileList(String projectIdentifier, String revision, Path path) {
        String string = UriBuilder.fromUri(configuration.getBasePath()).path("/a/").path(projectIdentifier).toString();
        return GitUtilities.onRepository(string, revision, rootDirectory -> {
            File relativeDirectory = rootDirectory.toPath().resolve(path).toFile();
            File[] files = Optional.ofNullable(relativeDirectory.listFiles()).orElseThrow();
            Collection<FileDto> collect = Stream.of(files)
                    .map(toFileDto(rootDirectory.toPath()))
                    .collect(Collectors.toSet());
            log.info("{}", collect);
            return collect;
        });
    }

    private static Function<File, FileDto> toFileDto(Path path) {
        return f -> {
            try {
                Path path1 = f.toPath();
                return new FileDto(path.relativize(path1), Files.size(path1), f.isFile() ? FileDto.Type.FILE : FileDto.Type.DIRECTORY);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public Collection<ProjectDto> getProjects() {
        return getListProjects().entrySet().stream()
                .filter(project -> !Objects.equals(project.getValue().getState(), ProjectInfo.StateEnum.HIDDEN))
                .map(project -> new ProjectDto(project.getKey(), statusMapper(project.getValue().getState())))
                .collect(toSet());
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
