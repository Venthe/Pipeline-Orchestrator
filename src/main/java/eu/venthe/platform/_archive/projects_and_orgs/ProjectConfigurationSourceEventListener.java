/*
package eu.venthe.pipeline.orchestrator.repositorys._archive.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.events.RepositorySourceConfigurationAddedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RepositoryConfigurationSourceEventListener {
    private final RepositorySourceConfigurationCommandService repositorysSourceConfigurationService;

    public RepositoryConfigurationSourceEventListener(MessageListenerRegistry listener, RepositorySourceConfigurationCommandService repositorysSourceConfigurationService) {
        this.repositorysSourceConfigurationService = repositorysSourceConfigurationService;

        listener.observe(RepositorySourceConfigurationAddedEvent.class, (envelope -> repositorySourceAdded(envelope.getData())));
    }

    public void repositorySourceAdded(RepositorySourceConfigurationAddedEvent event) {
        log.info("Received RepositorySourceConfigurationAddedEvent. {}", event);
        repositorysSourceConfigurationService.synchronizeRepository(event.getSourceId());
    }
}
*/
