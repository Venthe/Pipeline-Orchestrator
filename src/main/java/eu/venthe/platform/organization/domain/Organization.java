package eu.venthe.platform.organization.domain;

import eu.venthe.platform.project.application.ProjectsQueryService;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import eu.venthe.platform.shared_kernel.events.MessageBroker;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import lombok.Value;

import java.util.List;

@Value
public class Organization {
    OrganizationId organizationId;
    Sources sources;
    ProjectsSynchronizer projectsSynchronizer;

    Organization(OrganizationId organizationId, Sources sources, MessageBroker messageBroker, ProjectsQueryService projectsQueryService) {
        this.sources = sources;
        this.organizationId = organizationId;
        this.projectsSynchronizer = new ProjectsSynchronizer(sources, messageBroker, projectsQueryService, organizationId);
    }

    public List<DomainTrigger> synchronize() {
        return projectsSynchronizer.synchronize();
    }

    // FIXME: Publish event
    public void addConfiguration(SourceId sourceId) {
        addConfiguration(sourceId);
    }

    // FIXME: Publish event
    public void addConfiguration(SourceId sourceId, Sources.SourceAlias alias) {
        sources.addConfiguration(sourceId, alias);
    }
}
