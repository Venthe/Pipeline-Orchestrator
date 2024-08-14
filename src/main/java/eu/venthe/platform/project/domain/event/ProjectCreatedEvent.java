package eu.venthe.platform.project.domain.event;

import eu.venthe.platform.project.domain.ProjectId;
import eu.venthe.platform.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class ProjectCreatedEvent implements DomainTrigger {
    ProjectId projectId;

    @Override
    public String getType() {
        return "project_created_event";
    }
}
