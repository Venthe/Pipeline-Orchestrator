package eu.venthe.platform.organization.domain;

import com.google.common.eventbus.EventBus;
import eu.venthe.platform.organization.application.model.CreateOrganizationSpecification;
import eu.venthe.platform.organization.domain.events.OrganizationCreatedEvent;
import eu.venthe.platform.organization.domain.source_configuration.plugins.PluginProvider;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.ProjectSourcePluginInstance;
import eu.venthe.platform.organization.domain.source_configuration.plugins.template.model.ProjectId;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class OrganizationFactory {
    private final PluginProvider provider;
    private final MessageBroker messageBroker;

    public Pair<Organization, List<DomainTrigger>> create(CreateOrganizationSpecification specification) {
        var id = specification.organizationId();
        var sources = Collections.singletonMap(SourceId.DEFAULT, provider.provide(specification.sourceConfigurationSpecification().sourceType(), specification.sourceConfigurationSpecification().properties()));
        var synchronizer = new ProjectsSynchronizer(messageBroker);
        var organization = new Organization(id, sources, synchronizer);
        return Pair.of(
                organization,
                Collections.singletonList(new OrganizationCreatedEvent())
        );
    }
}
