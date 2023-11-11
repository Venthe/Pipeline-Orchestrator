package eu.venthe.pipeline.pipeline_mediator.domain.plugins;

import com.mongodb.lang.Nullable;
import eu.venthe.pipeline.pipeline_mediator.api.MediatorController;
import eu.venthe.pipeline.pipeline_mediator.api.model.EventTriggerDto;
import eu.venthe.pipeline.pipeline_mediator.infrastructure.queue.kafka.QueuedEvent;
import lombok.NonNull;

import java.util.Optional;

public interface MediatorService {
    @Deprecated
    Optional<QueuedEvent> triggerManualEvent(@NonNull EventTriggerDto trigger);

    @Deprecated
    void receiveQueuedEvent(QueuedEvent event);

    @Deprecated
    void registerFailedQueuedEvent(QueuedEvent event);

    @Deprecated
    Optional<QueuedEvent> retriggerEvent(String referenceId);

    @Deprecated
    void changeStatus(MediatorController.Status payload);


    /**
     *
     * @return reference ID
     */
    String queue(RepositoryEvent event);

    record RepositoryEvent(String type, Metadata metadata){
        record Metadata(String branchName, String revisionId, @Nullable String tag) {}
    }
}
