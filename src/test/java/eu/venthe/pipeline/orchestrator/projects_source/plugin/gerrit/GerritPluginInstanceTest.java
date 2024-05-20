package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.FileDto;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Collection;

class GerritPluginInstanceTest {
    static GerritConfiguration CONFIGURATION = GerritConfiguration.builder()
            .basePath("http://localhost:15480")
            .username("admin")
            .password("secret")
            .build();

    @Test
    void listProjects() {
        ProjectSourcePlugin.PluginInstance plugin = new GerritPluginInstance(CONFIGURATION);

        Collection<ProjectDto> projects = plugin.getProjects();

        Assertions.assertThat(projects).hasSize(2)
                .containsExactlyInAnyOrder(
                        new ProjectDto("All-Projects", ProjectStatus.ACTIVE),
                        new ProjectDto("All-Users", ProjectStatus.ACTIVE)
                );
    }

    @Test
    void listFiles() {
        ProjectSourcePlugin.PluginInstance plugin = new GerritPluginInstance(CONFIGURATION);

        Collection<FileDto> files = plugin.getFileList("All-Projects", "refs/meta/config", Paths.get("."));

        Assertions.assertThat(files).hasSize(3)
                .containsExactlyInAnyOrder(
                        new FileDto(Paths.get("project.config"), 2223L, FileDto.Type.FILE),
                        new FileDto(Paths.get(".git"), 4096L, FileDto.Type.DIRECTORY),
                        new FileDto(Paths.get("groups"), 336L, FileDto.Type.FILE)
                );
    }
}
