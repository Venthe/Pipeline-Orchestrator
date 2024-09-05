package eu.venthe.platform.repository.plugin.filesystem;

import eu.venthe.platform.repository.plugin.PluginProvider;
import eu.venthe.platform.repository.plugin.template.Repository;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.dynamic_value.StringDynamicValue;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileSystemUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
class FilesystemRepositorySourcePluginInstanceTest {
    private Path temporaryDirectory;
    private PluginProvider pluginProvider = new PluginProvider(Set.of(new FilesystemRepositorySourcePlugin()));

    @BeforeEach
    void setup() throws IOException {
        temporaryDirectory = Files.createTempDirectory("test_%s".formatted(UUID.randomUUID().toString()));
        temporaryDirectory.toFile().deleteOnExit();
    }

    @AfterEach
    void teardown() throws IOException {
        FileSystemUtils.deleteRecursively(temporaryDirectory);
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
                .containsExactlyInAnyOrder(
                        new Repository("Repository-1"),
                        new Repository("Repository-2")
                );
    }

    @SneakyThrows
    private void createRepository(String other) {
        var repository = temporaryDirectory.resolve(other).toFile();
        repository.mkdir();
        Git.init().setDirectory(repository).call().close();
    }

    public RepositorySourcePluginInstance providePlugin() {
        return pluginProvider.provide("filesystem", Map.of("path", new StringDynamicValue(temporaryDirectory.toString())));
    }
}
