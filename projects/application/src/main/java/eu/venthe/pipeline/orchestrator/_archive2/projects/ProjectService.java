package eu.venthe.pipeline.orchestrator._archive2.projects;

import eu.venthe.pipeline.orchestrator._archive2.plugins.api.ProjectProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectProvider projectProvider;


}
