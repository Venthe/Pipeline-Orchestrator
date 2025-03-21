package eu.venthe.platform.projects.domain;

import eu.venthe.platform.projects.domain.events.RegisterProjectCommand;
import eu.venthe.platform.projects.domain.events.SynchronizeProjectsCommand;
import eu.venthe.platform.projects.plugin.template.Repository;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.events.DomainMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class SourceConfiguration {
    @EqualsAndHashCode.Include
    private final SourceConfigurationInternalIdentifier internalIdentifier;
    private final RepositorySourcePluginInstance plugin;

    SourceConfiguration(@NonNull SourceConfigurationInternalIdentifier identifier,
                        RepositorySourcePluginInstance plugin) {
        this.internalIdentifier = identifier;
        this.plugin = plugin;
    }

    public void visit(SourceConfigurationVisitor visitor) {
        visitor.setInternalIdentifier(internalIdentifier);
        visitor.setType(plugin.getSourceType());
    }

    public Set<ManagedRepository> getAllRepositories() {
        return plugin.getAllRepositories().stream()
                .map(SourceConfiguration::toManagedRepository)
                .collect(Collectors.toSet());
    }

    public Collection<DomainMessage> synchronizeAll() {
        var createRepositoryEvents = plugin.getAllRepositories().stream()
                .map(Repository::correlationId)
                .collect(Collectors.toSet()).stream()
                .<DomainMessage>map(correlationId -> new RegisterProjectCommand(getInternalIdentifier(), correlationId));

        var synchronizeRepositoriesCommand = new SynchronizeProjectsCommand(getInternalIdentifier());

        return Stream.concat(
                Stream.of(synchronizeRepositoriesCommand),
                createRepositoryEvents
        ).collect(Collectors.toSet());
    }

    public Optional<ManagedRepository> getRepository(ProjectCorrelationId projectCorrelationId) {
        return plugin.getRepository(projectCorrelationId)
                .map(SourceConfiguration::toManagedRepository);
    }

    private static ManagedRepository toManagedRepository(Repository repository) {
        return new ManagedRepository(
                repository.correlationId(),
                repository.trackedBranch(),
                repository.trackedBranchHash()
        );
    }
}
