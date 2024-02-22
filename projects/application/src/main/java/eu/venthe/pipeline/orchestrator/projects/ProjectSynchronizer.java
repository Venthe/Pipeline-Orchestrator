package eu.venthe.pipeline.orchestrator.projects;

import eu.venthe.pipeline.orchestrator.plugins.ProjectProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectSynchronizer {
    private final ProjectProvider projectProvider;

    public void test() {
        log.info("{}", projectProvider.getProjects());
    }
}
