package eu.venthe.platform.registries.domain;

import eu.venthe.platform.registries.domain.plugins.RegistryPluginInstance;
import eu.venthe.platform.registries.domain.plugins.RegistryType;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.MODULE)
public class Registry implements AutoCloseable {

    @Getter
    @EqualsAndHashCode.Include
    private final RegistryIdentifier identifier;
    private final RegistryPluginInstance registryPlugin;

    private Listener listener;

    public boolean checkHealth() {
        log.trace("Checking registry {} health.", identifier.toString());

        return registryPlugin.checkHealth();
    }

    public RegistryType getType() {
        return registryPlugin.getType();
    }

    public void listen() {
        if (listener == null) {
            log.warn("Registry {} is already listening.", identifier.toString());
            return;
        }

        listener = registryPlugin.listen(this);
    }

    public Collection<UnmanagedRepository> getRepositories() {
        return registryPlugin.getRepositories().stream()
                .map(repositoryBuilder -> repositoryBuilder.build(identifier))
                .collect(Collectors.toSet());
    }

    @Override
    public void close() throws IOException {
        listener.stop();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
