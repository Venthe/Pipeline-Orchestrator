package eu.venthe.pipeline.orchestrator.shared_kernel;

import java.util.Collection;
import java.util.Optional;

public interface DomainRepository<AGGREGATE extends Aggregate<AGGREGATE_ID>, AGGREGATE_ID> {
    void save(AGGREGATE configuration);

    Optional<AGGREGATE> find(AGGREGATE_ID projectSourceConfigurationId);

    void delete(AGGREGATE_ID projectSourceConfigurationId);

    Collection<AGGREGATE> findAll();
}
