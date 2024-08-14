package eu.venthe.platform.namespace.domain;

import eu.venthe.platform.namespace.application.model.CreateNamespaceSpecification;
import eu.venthe.platform.namespace.domain.events.NamespaceCreatedEvent;
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
public class NamespaceFactory {
    private final SourceConfigurationManager sourceConfigurationManager;
    private final SourceQueryService sourceQueryService;
    private final MessageBroker messageBroker;
    private final ProjectsQueryService projectsQueryService;

    public Pair<Namespace, List<DomainTrigger>> create(CreateNamespaceSpecification specification) {

        var source = sourceConfigurationManager.register(specification.source());
        var organization = new Namespace(specification.namespaceName(), new Namespace.Source(source, sourceQueryService), messageBroker, projectsQueryService);

        return Pair.of(
                organization,
                Collections.singletonList(new NamespaceCreatedEvent(organization.getNamespaceName()))
        );
    }
}
