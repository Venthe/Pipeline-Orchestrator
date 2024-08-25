package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.shared_kernel.system_events.RepositoryEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class EventData<T extends RepositoryEvent> {
    private final T event;
}
