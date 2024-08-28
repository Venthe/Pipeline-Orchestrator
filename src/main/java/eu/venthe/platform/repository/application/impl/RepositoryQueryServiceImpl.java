package eu.venthe.platform.repository.application.impl;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.repository.application.RepositoryQueryService;
import eu.venthe.platform.repository.application.model.RepositoryDto;
import eu.venthe.platform.repository.domain.Repository;
import eu.venthe.platform.repository.domain.RepositoryName;
import eu.venthe.platform.repository.domain.infrastructure.RepositoryRepository;
import eu.venthe.platform.shared_kernel.git.RevisionShortName;
import eu.venthe.platform.shared_kernel.io.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RepositoryQueryServiceImpl implements RepositoryQueryService {
    private final RepositoryRepository repositoryRepository;
    private final FeatureManager featureManager;

    @Override
    public Collection<RepositoryDto> listRepository() {
        return repositoryRepository.findAll().stream().map(RepositoryQueryServiceImpl::toRepositoryDto).collect(Collectors.toSet());
    }

    @Override
    public Optional<RepositoryDto> find(RepositoryName repositoryId) {
        return repositoryRepository.find(repositoryId).map(RepositoryQueryServiceImpl::toRepositoryDto);
    }

    @Override
    public Stream<RepositoryName> getRepositoryNames(OrganizationName organizationName) {
        return repositoryRepository.findAll().stream().map(Repository::getId).filter(id -> id.organizationName().equals(organizationName));
    }

    @Override
    public Optional<File> getFile(final RepositoryName id, final RevisionShortName revision, final Path file) {
        if (!featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            throw new UnsupportedOperationException();
        }

        return repositoryRepository.find(id).orElseThrow().getFile(revision, file);
    }

    private static RepositoryDto toRepositoryDto(Repository p) {
        return new RepositoryDto(p.getId(), p.getStatus());
    }
}
