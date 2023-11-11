package eu.venthe.pipeline.pipeline_mediator.infrastructure.database.mongodb;

import eu.venthe.pipeline.pipeline_mediator.domain.model.AbstractContinuousIntegrationEvent;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

@ConditionalOnProperty(name = "infrastructure.database.type", havingValue = "mongodb")
@Transactional
public interface ContinuousIntegrationEventRepository extends MongoRepository<AbstractContinuousIntegrationEvent, ObjectId>, ContinuousIntegrationEventCustom {
}
