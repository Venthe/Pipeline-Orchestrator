package eu.venthe.platform.repository.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class SourceConfiguration {
    @EqualsAndHashCode.Include
    private final String name;

    public SourceConfiguration(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new InvalidSourceConfigurationNameException(name);
        }

        this.name = name;
    }

    public void visit(SourceConfigurationVisitor visitor) {
        visitor.setName(name);
    }
}
