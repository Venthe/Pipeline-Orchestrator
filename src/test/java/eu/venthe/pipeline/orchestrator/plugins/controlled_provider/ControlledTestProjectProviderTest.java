/*
package eu.venthe.pipeline.orchestrator.plugins.controlled_provider;

import eu.venthe.pipeline.orchestrator.projects.adapter.template.model.ProjectDto;
import eu.venthe.pipeline.orchestrator.projects.adapter.plugin.controlled_provider.ControlledTestProjectProvider;
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
*/
