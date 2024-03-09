package eu.venthe.pipeline.orchestrator.project_configuration_sources.core.application;

import eu.venthe.pipeline.orchestrator.AbstractIntegrationTest;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainMessageBroker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@MockBean(classes = {DomainMessageBroker.class})
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