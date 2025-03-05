package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.RegisterProjectCommand;
import eu.venthe.platform.projects.domain.events.SynchronizeProjectsCommand;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.events.DomainMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class SourceConfiguration {
    @EqualsAndHashCode.Include
    private final String identifier;
    private final RepositorySourcePluginInstance plugin;

    SourceConfiguration(String identifier, RepositorySourcePluginInstance plugin) {
        if (identifier == null || identifier.isBlank()) {
            throw new InvalidSourceConfigurationIdentifierException(identifier);
        }

        this.identifier = identifier;
        this.plugin = plugin;
    }

    public void visit(SourceConfigurationVisitor visitor) {
        visitor.setIdentifier(identifier);
        visitor.setType(plugin.getSourceType());
    }

    public Set<ManagedRepository> getAllRepositories() {
        return plugin.getAllRepositories().stream()
                .map(SourceConfiguration::toManagedRepository)
                .collect(Collectors.toSet());
    }

    public Collection<DomainMessage> synchronizeAll() {
        var createRepositoryEvents = plugin.getAllRepositories().stream()
                .map(Repository::repositoryName)
                .collect(Collectors.toSet()).stream()
                .<DomainMessage>map(repositoryName -> new RegisterProjectCommand(getIdentifier(), repositoryName));

        var synchronizeRepositoriesCommand = new SynchronizeProjectsCommand(getIdentifier());

        return Stream.concat(
                Stream.of(synchronizeRepositoriesCommand),
                createRepositoryEvents
        ).collect(Collectors.toSet());
    }

    public Optional<ManagedRepository> getRepository(String repositoryName) {
        return plugin.getRepository(repositoryName)
                .map(SourceConfiguration::toManagedRepository);
    }

    private static ManagedRepository toManagedRepository(Repository repository) {
        return new ManagedRepository(repository.repositoryName(), repository.trackedBranch(), repository.trackedBranchHash());
    }
}
