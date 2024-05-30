package eu.venthe.pipeline.orchestrator.projects.shared_kernel.commands;

import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import lombok.Value;

@Value
public class UpdateProjectCommand implements DomainTrigger {
    String type = "update_project";
    ProjectId e;
}
