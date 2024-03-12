package eu.venthe.pipeline.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.venthe.pipeline.gerrit.api.ChangesApi;
import eu.venthe.pipeline.gerrit.api.ProjectsApi;
import eu.venthe.pipeline.gerrit.model.ProjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@Slf4j
@SpringBootTest
class TestTest {

    @Autowired
    ProjectsApi projectsApi;

    @Autowired
    ChangesApi changesApi;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void name() {
        Map<String, ProjectInfo> result = projectsApi.listProjects(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        log.info("{}", result);
    }

}
