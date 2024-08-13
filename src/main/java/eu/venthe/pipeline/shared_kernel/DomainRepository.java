package eu.venthe.pipeline.shared_kernel;

import java.util.Collection;
import java.util.Optional;

public interface DomainRepository<AGGREGATE extends Aggregate<AGGREGATE_ID>, AGGREGATE_ID> {
    void save(AGGREGATE aggregate);

    Optional<AGGREGATE> find(AGGREGATE_ID id);

    void delete(AGGREGATE_ID id);

    Collection<AGGREGATE> findAll();

    boolean exists(AGGREGATE_ID id);
}
