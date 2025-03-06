package eu.venthe.platform.projects.plugin.filesystem;

import eu.venthe.platform.projects.domain.ProjectCorrelationId;
import eu.venthe.platform.projects.plugin.PluginProvider;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.dynamic_value.StringDynamicValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
class FilesystemRepositorySourcePluginInstanceTest {
    private Path temporaryDirectory;
    private PluginProvider pluginProvider = new PluginProvider(Set.of(new FilesystemRepositorySourcePlugin()));

    @BeforeEach
    void setup() throws IOException {
        temporaryDirectory = Files.createTempDirectory("test_%s".formatted(UUID.randomUUID().toString()));
        temporaryDirectory.toFile().deleteOnExit();
    }

    @Test
    void repositoriesAreDetected() {
        // Given
        createRepository("Repository-1");
        createRepository("Repository-2");
        var pluginInstance = providePlugin();

        // When
        var repositories = pluginInstance.getAllRepositories();

        // Then
        Assertions.assertThat(repositories)
                .satisfiesExactlyInAnyOrder(
                        assertRepository("Repository-1"),
                        assertRepository("Repository-2")
                );
    }

    private static Consumer<Repository> assertRepository(String correlationId) {
        return repository -> {
            String shaRegex = "^[a-fA-F0-9]{40}$";
            Assertions.assertThat(repository.trackedBranchHash()).matches(shaRegex);
            Assertions.assertThat(repository.trackedBranch()).isEqualTo("refs/heads/main");
            Assertions.assertThat(repository.correlationId()).isEqualTo(new ProjectCorrelationId(correlationId));
        };
    }

    @SneakyThrows
    private void createRepository(String other) {
        var repository = temporaryDirectory.resolve(other).toFile();
        repository.mkdir();
        var git = Git.init().setDirectory(repository).call();
        git.commit().setAllowEmpty(true).setMessage("Initial message").call();
        git.close();
    }

    public RepositorySourcePluginInstance providePlugin() {
        return pluginProvider.provide("filesystem", Map.of("path", new StringDynamicValue(temporaryDirectory.toString())));
    }
}
