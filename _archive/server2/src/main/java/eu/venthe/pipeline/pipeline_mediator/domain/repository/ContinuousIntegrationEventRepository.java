package eu.venthe.pipeline.pipeline_mediator.domain.repository;

import eu.venthe.pipeline.pipeline_mediator.domain.model.AbstractContinuousIntegrationEvent;

import java.util.Optional;

public class ContinuousIntegrationEventRepository {
    public void save(AbstractContinuousIntegrationEvent ciEvent) {

    }

    public Optional<AbstractContinuousIntegrationEvent> findByReferenceId(String referenceId) {
        return null;
    }
}
