package eu.venthe.pipeline.orchestrator.projects_source.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


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
        projectSourceConfigurationFactory = new ProjectSourceConfigurationFactory(Set.of(new ControlledTestProviderPlugin(versionControlSystem, projectProvider)));
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

        assertThat(result).containsExactlyInAnyOrder(
                new ProjectDiscoveredEvent("Simple-Dispatch", configurationId)
        );
        assertThat(configuration.getKnownProjects())
                .containsExactlyInAnyOrder(
                        new Project(Project.Id.of(configurationId, "Simple-Dispatch"))
                );
    }

    @Test
    void shouldLoadProjectsFromMemory() {
        shouldLoadProjectsFromDirectory();

        projectProvider.addProject(new ProjectDto(ProjectDto.Status.ACTIVE, "Test-Project"));

        Collection<DomainEvent> result = configuration.synchronize();

        assertThat(result).containsExactlyInAnyOrder(
                new ProjectDiscoveredEvent("Test-Project", configurationId)
        );

        assertThat(configuration.getKnownProjects())
                .containsExactlyInAnyOrder(
                        new Project(Project.Id.of(configurationId, "Simple-Dispatch")),
                        new Project(Project.Id.of(configurationId, "Test-Project"))
                );
    }
}
