package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.plugins.projects.ProjectDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

class ControlledTestProjectProviderTest {
    private ControlledTestProjectProvider provider;

    @BeforeEach
    void setup() {
        provider = new ControlledTestProjectProvider(new RestTemplate());
    }

    @Test
    void name() {
        Collection<ProjectDto> projects = provider.getProjects();
        assertThat(projects)
                .containsExactlyInAnyOrder(new ProjectDto(ProjectDto.Status.ACTIVE, "Example-A"));
    }
}
