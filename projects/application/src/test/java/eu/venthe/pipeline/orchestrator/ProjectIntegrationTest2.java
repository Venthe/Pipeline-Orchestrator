package eu.venthe.pipeline.orchestrator;

import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@TestPropertySource(properties = {
        "togglz.features.PROJECTS_SOURCE_CONFIGURATION_FACTORY_WIP.enabled=true",
        "togglz.features.PROJECTS_SERVICE_WIP.enabled=true",
})
class ProjectIntegrationTest2 extends AbstractIntegrationTest {
    @Autowired
    ProjectsApi projectsApi;

    @Test
    void name() {
        log.info("{}", projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
    }
}
