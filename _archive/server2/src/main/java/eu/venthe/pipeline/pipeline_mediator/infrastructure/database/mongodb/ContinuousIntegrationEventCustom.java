package eu.venthe.pipeline.pipeline_mediator.infrastructure.database.mongodb;

import eu.venthe.pipeline.pipeline_mediator.domain.model.AbstractContinuousIntegrationEvent;
import lombok.NonNull;

import java.util.Optional;

public interface ContinuousIntegrationEventCustom {
    Optional<AbstractContinuousIntegrationEvent> findByReferenceId(@NonNull String referenceId);
}
