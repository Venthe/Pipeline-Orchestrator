package eu.venthe.platform.organization.domain;

import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.events.OrganizationCreatedEvent;
import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.application.SourceConfigurationManager;
import eu.venthe.platform.source_configuration.application.SourceQueryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrganizationFactory {
    private final SourceConfigurationManager sourceConfigurationManager;
    private final SourceQueryService sourceQueryService;
    private final MessageBroker messageBroker;
    private final ProjectsQueryService projectsQueryService;

    public Pair<Organization, List<DomainTrigger>> create(CreateOrganizationSpecification specification) {

        var source = sourceConfigurationManager.register(specification.source());
        var organization = new Organization(specification.organizationName(), new Organization.Source(source, sourceQueryService), messageBroker, projectsQueryService);

        return Pair.of(
                organization,
                Collections.singletonList(new OrganizationCreatedEvent(organization.getOrganizationName()))
        );
    }
}
