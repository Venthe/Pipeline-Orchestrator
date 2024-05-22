/*
package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestProjectProvider;
import eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestProviderPlugin;
import eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestVersionControlSystem;
import eu.venthe.pipeline.orchestrator.projects.adapter.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects._archive.api.events.ProjectDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.projects._archive.api.events.ProjectSourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.projects._archive.domain.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainEvent;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestProviderPlugin.SOURCE_TYPE;
import static eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestProviderPlugin.generateId;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
class ProjectSourceConfigurationTest {
    @InjectSoftAssertions
    private SoftAssertions sa;

    private ProjectSourceConfigurationFactory projectSourceConfigurationFactory;
    private ControlledTestVersionControlSystem versionControlSystem;
    private ControlledTestProjectProvider projectProvider;

    private String configurationId;
    private ProjectSourceConfiguration configuration;

    @BeforeEach
    void setup() {
        versionControlSystem = new ControlledTestVersionControlSystem();
        projectProvider = new ControlledTestProjectProvider();
        projectSourceConfigurationFactory = new ProjectSourceConfigurationFactoryImpl(Set.of(new ControlledTestProviderPlugin(versionControlSystem, projectProvider)));
        configurationId = generateId();
    }

    @Test
    void shouldRegisterConfiguration() {
        var result = projectSourceConfigurationFactory.createConfiguration(
                configurationId,
                SOURCE_TYPE,
                new HashMap<>()
        );

        configuration = result.getLeft();
        sa.assertThat(configuration).satisfies(
                _configuration -> sa.assertThat(_configuration.getId()).isEqualTo(ProjectSourceConfigurationId.of(configurationId)),
                _configuration -> sa.assertThat(_configuration.getSourceType()).isEqualTo(SOURCE_TYPE)
        );
        sa.assertThat(result.getRight()).containsExactlyInAnyOrder(
                new ProjectSourceConfigurationAddedEvent(configurationId, SOURCE_TYPE)
        );
    }

    @Test
    void shouldLoadProjectsFromDirectory() {
        shouldRegisterConfiguration();

        assertThat(configuration.getKnownProjects()).isEmpty();

        Collection<DomainEvent> result = configuration.synchronize();

        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new ProjectDiscoveredEvent("Simple-Dispatch", configurationId)
        );
        assertThat(configuration.getKnownProjects())
                .containsExactlyInAnyOrder(
                        new KnownProject("Simple-Dispatch", configurationId)
                );
    }

    @Test
    void shouldLoadProjectsFromMemory() {
        shouldLoadProjectsFromDirectory();

        projectProvider.addProject(new ProjectDto(ProjectDto.Status.ACTIVE, "Test-Project"));

        Collection<DomainEvent> result = configuration.synchronize();

        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new ProjectDiscoveredEvent("Test-Project", configurationId)
        );

        assertThat(configuration.getKnownProjects())
                .containsExactlyInAnyOrder(
                        new KnownProject("Simple-Dispatch", configurationId),
                        new KnownProject("Test-Project", configurationId)
                );
    }
}
*/
