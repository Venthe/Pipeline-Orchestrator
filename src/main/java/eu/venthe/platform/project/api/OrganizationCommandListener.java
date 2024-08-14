package eu.venthe.platform.project.api;

import eu.venthe.platform.namespace.domain.events.CreateProjectCommand;
import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.model.CreateProjectSpecification;
import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import org.springframework.stereotype.Component;

@Component
public class OrganizationCommandListener {
    private OrganizationCommandListener(final MessageListenerRegistry registry, final ProjectsCommandService projectsCommandService, final SourceQueryService sourceQueryService) {
        registry.register(CreateProjectCommand.class, new MessageListenerRegistry.Observer<>("Create project command listener", envelope -> {
            var data = envelope.getData();
            var namespaceName = data.getNamespaceName();
            var configurationSourceId = data.getProjectId().configurationSourceId();
            var sourceProjectId = data.getProjectId().sourceProjectId();

            var project = sourceQueryService.getProject(configurationSourceId, sourceProjectId).orElseThrow();

            projectsCommandService.add(new CreateProjectSpecification(new ProjectId(namespaceName, sourceProjectId), configurationSourceId, project.project().status()));
        }));
    }
}
