package eu.venthe.pipeline.orchestrator._archive2.projects;

import eu.venthe.pipeline.orchestrator.plugins.ProjectProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectProvider projectProvider;


}
