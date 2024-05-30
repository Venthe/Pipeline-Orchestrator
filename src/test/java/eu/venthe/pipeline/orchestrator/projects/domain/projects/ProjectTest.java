/*
package eu.venthe.pipeline.orchestrator.projects.domain.projects;

import eu.venthe.pipeline.orchestrator.projects.domain.Project;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectsSourceConfiguration;
import eu.venthe.pipeline.orchestrator.projects.domain.model.SourceType;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects.shared_kernel.events.ProjectAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.venthe.pipeline.orchestrator.projects.domain.projects.model.ProjectStatus.ACTIVE;

class ProjectTest {
    private final static SourceType EXAMPLE_SOURCE_TYPE = new SourceType("Gerrit");
    private final static String EXAMPLE_NAME = "All-Projects";
    private final static ProjectId EXAMPLE_PROJECT_ID = ProjectId.of(EXAMPLE_SOURCE_TYPE, EXAMPLE_NAME);
    private static final ProjectStatus EXAMPLE_PROJECT_STATUS = ACTIVE;
    private static final ProjectsSourceConfiguration EMPTY_CONFIGURATION = null;
    private final ProjectFactory factory = new ProjectFactory();

    @Test
    void name() {
        ProjectStatus status = EXAMPLE_PROJECT_STATUS;

        List<DomainTrigger> events = factory.create(EXAMPLE_PROJECT_ID, status, EMPTY_CONFIGURATION).getValue();

        Assertions.assertThat(events).containsExactly(new ProjectAddedEvent(EXAMPLE_PROJECT_ID, status));
    }

    @Test
    void name2() {
        Project project = factory.create(EXAMPLE_PROJECT_ID, EXAMPLE_PROJECT_STATUS, EMPTY_CONFIGURATION).getKey();

        Assertions.assertThat(project.getId()).isEqualTo(EXAMPLE_PROJECT_ID);
        Assertions.assertThat(project.getStatus()).isEqualTo(EXAMPLE_PROJECT_STATUS);
        Assertions.assertThat(project.getDescription()).isEmpty();
    }
}
*/
