package eu.venthe.pipeline.orchestrator.projects_source.core.application;

import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

class ProjectsSourceConfigurationServiceIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    ProjectsSourceConfigurationService projectsSourceConfigurationService;

    @Nested
    class listConfigurations {
        @Test
        void onNoConfiguration() {
            // given

            // when
            var result = projectsSourceConfigurationService.listConfigurations();

            // then
            Assertions.assertThat(result).isEmpty();
        }
    }
}