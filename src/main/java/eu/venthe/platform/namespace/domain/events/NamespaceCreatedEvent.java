package eu.venthe.platform.namespace.domain.events;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
@Getter
public class NamespaceCreatedEvent implements DomainTrigger {
    private final NamespaceName namespaceName;

    @Override
    public String getType() {
        return "namespace_created";
    }
}
