/*
package eu.venthe.pipeline.orchestrator.repositorys.domain;

import eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestRepositoryProvider;
import eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestProviderPlugin;
import eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestVersionControlSystem;
import eu.venthe.pipeline.orchestrator.repositorys.adapter.template.model.RepositoryDto;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.events.RepositoryDiscoveredEvent;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.events.RepositorySourceConfigurationAddedEvent;
import eu.venthe.pipeline.orchestrator.repositorys._archive.domain.*;
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

import static eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestProviderPlugin.SOURCE_TYPE;
import static eu.venthe.pipeline.orchestrator.repositorys.adapter.plugin.controlled_provider.ControlledTestProviderPlugin.generateId;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SoftAssertionsExtension.class)
class RepositorySourceConfigurationTest {
    @InjectSoftAssertions
    private SoftAssertions sa;

    private RepositorySourceConfigurationFactory repositorySourceConfigurationFactory;
    private ControlledTestVersionControlSystem versionControlSystem;
    private ControlledTestRepositoryProvider repositoryProvider;

    private String configurationId;
    private RepositorySourceConfiguration configuration;

    @BeforeEach
    void setup() {
        versionControlSystem = new ControlledTestVersionControlSystem();
        repositoryProvider = new ControlledTestRepositoryProvider();
        repositorySourceConfigurationFactory = new RepositorySourceConfigurationFactoryImpl(Set.of(new ControlledTestProviderPlugin(versionControlSystem, repositoryProvider)));
        configurationId = generateId();
    }

    @Test
    void shouldRegisterConfiguration() {
        var result = repositorySourceConfigurationFactory.createConfiguration(
                configurationId,
                SOURCE_TYPE,
                new HashMap<>()
        );

        configuration = result.getLeft();
        sa.assertThat(configuration).satisfies(
                _configuration -> sa.assertThat(_configuration.getId()).isEqualTo(RepositorySourceConfigurationId.of(configurationId)),
                _configuration -> sa.assertThat(_configuration.getSourceType()).isEqualTo(SOURCE_TYPE)
        );
        sa.assertThat(result.getRight()).containsExactlyInAnyOrder(
                new RepositorySourceConfigurationAddedEvent(configurationId, SOURCE_TYPE)
        );
    }

    @Test
    void shouldLoadRepositoryFromDirectory() {
        shouldRegisterConfiguration();

        assertThat(configuration.getKnownRepository()).isEmpty();

        Collection<DomainEvent> result = configuration.synchronize();

        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new RepositoryDiscoveredEvent("Simple-Dispatch", configurationId)
        );
        assertThat(configuration.getKnownRepository())
                .containsExactlyInAnyOrder(
                        new KnownRepository("Simple-Dispatch", configurationId)
                );
    }

    @Test
    void shouldLoadRepositoryFromMemory() {
        shouldLoadRepositoryFromDirectory();

        repositoryProvider.addRepository(new RepositoryDto(RepositoryDto.Status.ACTIVE, "Test-Repository"));

        Collection<DomainEvent> result = configuration.synchronize();

        Assertions.assertThat(result).containsExactlyInAnyOrder(
                new RepositoryDiscoveredEvent("Test-Repository", configurationId)
        );

        assertThat(configuration.getKnownRepository())
                .containsExactlyInAnyOrder(
                        new KnownRepository("Simple-Dispatch", configurationId),
                        new KnownRepository("Test-Repository", configurationId)
                );
    }
}
*/
