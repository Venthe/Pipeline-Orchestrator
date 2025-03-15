package eu.venthe.platform.registries.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public class Registry {

    @Getter
    @EqualsAndHashCode.Include
    private final RegistryIdentifier identifier;
}
