package eu.venthe.platform.organization.application.impl;

import eu.venthe.platform.organization.application.OrganizationCommandService;
import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.OrganizationFactory;
import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.organization.domain.infrastructure.OrganizationRepository;
import eu.venthe.platform.shared_kernel.events.Envelope;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationCommandServiceImpl implements OrganizationCommandService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationFactory organizationFactory;
    private final MessageBroker messageBroker;

    @Override
    public OrganizationName register(final CreateOrganizationSpecification specification) {
        if (organizationRepository.exists(specification.organizationName())) {
            throw new UnsupportedOperationException("Organization \"%s\" already exists".formatted(specification.organizationName().value()));
        }

        log.trace("Creating organization. {}", specification);
        var result = organizationFactory.create(specification);
        organizationRepository.save(result.getKey());
        log.info("Organization \"{}\" created.", result.getKey().getOrganizationName());

        messageBroker.exchange(Envelope.from(result.getRight()));

        return specification.organizationName();
    }

    @Override
    public void synchronize(final OrganizationName name) {
        var organization = organizationRepository.find(name).orElseThrow();
        messageBroker.exchange(Envelope.from(organization.synchronize()));
    }

    @Override
    public void archive(final OrganizationName organizationName) {
        throw new UnsupportedOperationException();
    }
}
