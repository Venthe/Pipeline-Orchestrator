package eu.venthe.platform.repository.api;

import eu.venthe.platform.organization.domain.events.CreateRepositoryCommand;
import eu.venthe.platform.repository.application.RepositoryCommandService;
import eu.venthe.platform.repository.application.model.CreateRepositorySpecification;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.shared_kernel.events.MessageListenerRegistry;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import org.springframework.stereotype.Component;

@Component
public class OrganizationCommandListener {
    private OrganizationCommandListener(final MessageListenerRegistry registry, final RepositoryCommandService repositorysCommandService, final SourceQueryService sourceQueryService) {
        registry.register(CreateRepositoryCommand.class, new MessageListenerRegistry.Observer<>("Create repository command listener", envelope -> {
            var data = envelope.getData();
            var organizationName = data.getOrganizationName();
            var configurationSourceId = data.getRepositoryName().configurationSourceId();
            var sourceRepositoryName = data.getRepositoryName().sourceRepositoryName();

            var repository = sourceQueryService.getRepository(configurationSourceId, sourceRepositoryName).orElseThrow();

            repositorysCommandService.add(new CreateRepositorySpecification(new RepositoryName(organizationName, sourceRepositoryName), configurationSourceId, repository.repository().status()));
        }));
    }
}
