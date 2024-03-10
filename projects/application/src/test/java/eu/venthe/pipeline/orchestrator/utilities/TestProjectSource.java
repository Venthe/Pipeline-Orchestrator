package eu.venthe.pipeline.orchestrator.utilities;

import eu.venthe.pipeline.orchestrator.infrastructure.repository.InMemoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestProjectSource {
    private final InMemoryRepository<TestProject, Long> repository = new InMemoryRepository<>();

    public void addProject(TestProject testProject) {
        log.info("Registering test project {}", testProject);
        repository.save(1L, testProject);
    }
}
