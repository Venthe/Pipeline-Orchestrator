package eu.venthe.platform.repository.domain;

import eu.venthe.platform.shared_kernel.DomainResult;
import eu.venthe.platform.repository.domain.events.SourceRegisteredEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class SourceConfiguration {
    @EqualsAndHashCode.Include
    private final String name;

    private SourceConfiguration(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new InvalidSourceConfigurationNameException(name);
        }

        this.name = name;
    }

    public static DomainResult<SourceConfiguration> create(String name) {
        var sourceConfiguration = new SourceConfiguration(name);
        return DomainResult.from(sourceConfiguration, new SourceRegisteredEvent(name));
    }

    public void visit(SourceConfigurationVisitor visitor) {
        visitor.setName(name);
    }
}
