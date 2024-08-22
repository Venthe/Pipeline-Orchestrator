/*
package eu.venthe.platform.application.repositorys.domain;

import eu.venthe.platform.repository.application.RepositoryCommandService;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.repository.application.model.CreateRepositorySpecification;
import eu.venthe.platform.repository.domain.RepositoryId;
import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePlugin;
import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class RepositorySourceConfigurationTest {
    @Mock
    RepositorySourcePlugin.PluginInstance pluginInstance;
    @Mock
    RepositoryCommandService repositoryCommands;
    @Spy
    RepositoryQueryService repositoryQueries;

    RepositorySourceConfiguration repositorysSourceConfiguration;

    @BeforeEach
    void beforeEach() {
        repositorysSourceConfiguration = RepositorySourceConfiguration.reconstitute(new SourceConfigurationId("TestName"), pluginInstance, repositoryCommands, repositoryQueries);
    }

    @Test
    void name() {
        Mockito.when(pluginInstance.getRepositoryIds()).thenReturn(Stream.of(new Repository.Id("123")));
        Mockito.when(repositoryQueries.getRepositoryIds(new SourceConfigurationId("TestName"))).thenReturn(Stream.empty());
        Mockito.when(pluginInstance.getRepository("123")).thenReturn(Optional.of(new Repository("123", RepositoryStatus.ACTIVE)));

        // FIXME: Uncomment
        // repositorysSourceConfiguration.synchronize();

        Mockito.verify(repositoryCommands).add(new SourceConfigurationId("TestName"), new CreateRepositorySpecification(RepositoryId.builder().name("123").configurationId(new SourceConfigurationId("TestName")).build(), RepositoryStatus.ACTIVE));
    }

    @Test
    void name2() {
        Repository expectedValue = new Repository("123", RepositoryStatus.ACTIVE);
        Mockito.when(pluginInstance.getRepository("123")).thenReturn(Optional.of(expectedValue));

        var repository = repositorysSourceConfiguration.getRepository("123");

        Assertions.assertThat(repository).isPresent().hasValue(expectedValue);
    }
}
*/
