package eu.venthe.platform.organization.domain;

import eu.venthe.platform.project.application.ProjectsCommandService;
import eu.venthe.platform.project.application.ProjectsQueryService;
import lombok.Value;

@Value
public class Organization {
    OrganizationId organizationId;
    ProjectsSynchronizer projectsSynchronizer;

    private Organization(final OrganizationId organizationId, ProjectsQueryService projectsQueryService, ProjectsCommandService projectsCommandService) {
        this.organizationId = organizationId;
        projectsSynchronizer = new ProjectsSynchronizer()
    }

    public boolean isActive() {
        // TODO: Add status logic
        return true;
    }

    public void synchronize() {
        projectsSynchronizer.synchronize();
    }
}
