package eu.venthe.platform.projects.api;

import eu.venthe.platform.projects.application.ProjectCommandService;
import eu.venthe.platform.projects.domain.events.RegisterProjectCommand;
import eu.venthe.platform.shared_kernel.events.DomainMessageListenerRegistry;
import org.springframework.stereotype.Service;

@Service
public class ProjectEventListener {
    public ProjectEventListener(DomainMessageListenerRegistry registry, ProjectCommandService projectCommandService) {
        registry.<RegisterProjectCommand>register(RegisterProjectCommand.REGISTER_PROJECT, message -> {
            projectCommandService.registerProject(message.sourceConfigurationIdentifier(), message.projectName());
        });
    }
}
