package eu.venthe.platform.application.infrastructure.in_memory_repository;

import eu.venthe.platform.shared_kernel.Aggregate;
import eu.venthe.platform.shared_kernel.DomainRepository;

import java.util.*;

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
        return getAll();
    }

    @Override
    public boolean exists(ID id) {
        return repository.getById(id).isPresent();
    }

    public void update(OBJECT object) {
        repository.update(object.getId(), object);
    }

    public void save(OBJECT aggregate) {
        repository.save(aggregate.getId(), aggregate);
    }

    @Override
    public Optional<OBJECT> find(ID id) {
        return repository.getById(id);
    }
}
