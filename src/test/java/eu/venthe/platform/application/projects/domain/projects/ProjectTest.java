/*
package eu.venthe.pipeline.orchestrator.repositorys.domain.repositorys;

import eu.venthe.pipeline.orchestrator.repositorys.domain.Repository;
import eu.venthe.pipeline.orchestrator.repositorys.domain.RepositorySourceConfiguration;
import eu.venthe.pipeline.orchestrator.repositorys.domain.model.SourceType;
import eu.venthe.pipeline.orchestrator.repositorys.domain.RepositoryId;
import eu.venthe.pipeline.orchestrator.repositorys.domain.repositorys.model.RepositoryStatus;
import eu.venthe.pipeline.orchestrator.repositorys.shared_kernel.events.RepositoryAddedEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.events.DomainTrigger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.venthe.pipeline.orchestrator.repositorys.domain.repositorys.model.RepositoryStatus.ACTIVE;

class RepositoryTest {
    private final static SourceType EXAMPLE_SOURCE_TYPE = new SourceType("Gerrit");
    private final static String EXAMPLE_NAME = "All-Repository";
    private final static RepositoryId EXAMPLE_PROJECT_ID = RepositoryId.of(EXAMPLE_SOURCE_TYPE, EXAMPLE_NAME);
    private static final RepositoryStatus EXAMPLE_PROJECT_STATUS = ACTIVE;
    private static final RepositorySourceConfiguration EMPTY_CONFIGURATION = null;
    private final RepositoryFactory factory = new RepositoryFactory();

    @Test
    void name() {
        RepositoryStatus status = EXAMPLE_PROJECT_STATUS;

        List<DomainTrigger> events = factory.create(EXAMPLE_PROJECT_ID, status, EMPTY_CONFIGURATION).getValue();

        Assertions.assertThat(events).containsExactly(new RepositoryAddedEvent(EXAMPLE_PROJECT_ID, status));
    }

    @Test
    void name2() {
        Repository repository = factory.create(EXAMPLE_PROJECT_ID, EXAMPLE_PROJECT_STATUS, EMPTY_CONFIGURATION).getKey();

        Assertions.assertThat(repository.getId()).isEqualTo(EXAMPLE_PROJECT_ID);
        Assertions.assertThat(repository.getStatus()).isEqualTo(EXAMPLE_PROJECT_STATUS);
        Assertions.assertThat(repository.getDescription()).isEmpty();
    }
}
*/
