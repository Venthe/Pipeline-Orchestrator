package eu.venthe.pipeline.orchestrator.infrastructure.in_memory_repositroy;

import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import eu.venthe.pipeline.orchestrator.shared_kernel.DomainRepository;

import java.util.*;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;

public class InMemoryDomainRepository<OBJECT extends Aggregate<ID>, ID> implements DomainRepository<OBJECT, ID> {
    private final InMemoryRepository<OBJECT, ID> repository = new InMemoryRepository<>();

    public Collection<OBJECT> getAll() {
        return repository.getAll();
    }

    public void delete(ID id) {
        repository.delete(id);
    }

    @Override
    public Collection<OBJECT> findAll() {
        return List.of();
    }

    public void update(OBJECT object) {
        repository.update(object.getId(), object);
    }

    public void save(OBJECT object) {
        repository.save(object.getId(), object);
    }

    @Override
    public Optional<OBJECT> find(ID projectSourceConfigurationId) {
        return Optional.empty();
    }
}
