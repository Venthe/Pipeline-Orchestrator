/*
package eu.venthe.pipeline.orchestrator.repositorys.api;

import eu.venthe.pipeline.orchestrator.infrastructure.message_broker.MessageListenerRegistry;
import eu.venthe.pipeline.orchestrator.repositorys.api.dto.CreateRepositorySpecificationDto;
import eu.venthe.pipeline.orchestrator.repositorys.api.dto.RepositoryDto;
import eu.venthe.pipeline.orchestrator.repositorys.api.dto.UpdateRepositorySpecificationDto;
import eu.venthe.pipeline.orchestrator.repositorys._archive.api.events.RepositoryDiscoveredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class RepositoryEventListener {
    private final RepositoryCommandService repositorysService;
    private final RepositoryQueryService queryService;

    public RepositoryEventListener(MessageListenerRegistry listener, RepositoryCommandService repositorysService, RepositoryQueryService queryService) {
        this.repositorysService = repositorysService;
        this.queryService = queryService;

        listener.observe(RepositoryDiscoveredEvent.class, (envelope -> repositoryDiscovered(envelope.getData())));
    }

    public void repositoryDiscovered(RepositoryDiscoveredEvent event) {
        log.info("Received RepositorySourceConfigurationAddedEvent. {}", event);

        Optional<RepositoryDto> repositoryDto = queryService.find(event.getSystemId(), event.getRepositoryName());
        if (repositoryDto.isEmpty()) {
            repositorysService.add(new CreateRepositorySpecificationDto(event.getRepositoryName(), event.getSystemId()));
        } else if (event.getStatus() != repositoryDto.get().getStatus() || !event.getRepositoryName().equalsIgnoreCase(repositoryDto.get().getName())) {
            repositorysService.updateDetails(event.getSystemId(), new UpdateRepositorySpecificationDto(event.getStatus(), event.getRepositoryName()));
        }
    }
}
*/
