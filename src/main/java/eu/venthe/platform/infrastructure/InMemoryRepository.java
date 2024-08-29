package eu.venthe.platform.infrastructure;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class InMemoryRepository<ID, OBJECT> {
    private final Map<ID, OBJECT> repository = new HashMap<>();

    public boolean exists(ID id) {
        return repository.get(id) != null;
    }

    public void save(ID id, OBJECT object) {
        repository.put(id, object);
    }

    public Optional<OBJECT> find(ID id) {
        return Optional.ofNullable(repository.get(id));
    }
}
