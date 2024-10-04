package eu.venthe.platform.repository.domain;

import eu.venthe.platform.shared_kernel.DomainResult;
import eu.venthe.platform.shared_kernel.events.DomainMessage;
import eu.venthe.platform.repository.domain.events.CreateRepositoryCommand;
import eu.venthe.platform.repository.domain.events.SourceRegisteredEvent;
import eu.venthe.platform.repository.domain.events.SynchronizeRepositoriesCommand;
import eu.venthe.platform.repository.plugin.template.Repository;
import eu.venthe.platform.repository.plugin.template.RepositorySourcePluginInstance;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class SourceConfiguration {
    @EqualsAndHashCode.Include
    private final String name;
    private final RepositorySourcePluginInstance plugin;

    private SourceConfiguration(String name, RepositorySourcePluginInstance plugin) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new InvalidSourceConfigurationNameException(name);
        }

        this.name = name;
        this.plugin = plugin;
    }

    public static DomainResult<SourceConfiguration> create(String name, RepositorySourcePluginInstance plugin) {
        var sourceConfiguration = new SourceConfiguration(name, plugin);
        return DomainResult.from(sourceConfiguration, new SourceRegisteredEvent(name));
    }

    public void visit(SourceConfigurationVisitor visitor) {
        visitor.setName(name);
        visitor.setType(plugin.getSourceType());
    }

    public Set<Repository> getAllRepositories() {
        return plugin.getAllRepositories();
    }

    public Collection<DomainMessage> synchronize() {
        var createRepositoryEvents = plugin.getAllRepositories().stream()
                .map(Repository::repositoryName)
                .collect(Collectors.toSet()).stream()
                .<DomainMessage>map(repositoryName -> new CreateRepositoryCommand(repositoryName, getName()));

        var synchronizeRepositoriesCommand = new SynchronizeRepositoriesCommand(getName());

        return Stream.concat(
                Stream.of(synchronizeRepositoriesCommand),
                createRepositoryEvents
        ).collect(Collectors.toSet());
    }
}
