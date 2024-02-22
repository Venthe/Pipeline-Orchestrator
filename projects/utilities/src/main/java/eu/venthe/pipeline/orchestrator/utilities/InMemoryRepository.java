package eu.venthe.pipeline.orchestrator.utilities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.unmodifiableCollection;
import static java.util.Optional.ofNullable;

public class InMemoryRepository<ID, OBJECT> {
    private final Map<ID, OBJECT> repository = new HashMap<>();

    public Optional<OBJECT> getById(ID id) {
        return ofNullable(repository.get(id));
    }

    public Collection<OBJECT> getAll() {
        return unmodifiableCollection(repository.values());
    }

    public boolean delete(ID id) {
        return ofNullable(repository.remove(id)).isPresent();
    }

    public void update(ID id, OBJECT object) {
        repository.put(id, object);
    }

    public void save(ID id, OBJECT object) {
        repository.put(id, object);
    }
}
