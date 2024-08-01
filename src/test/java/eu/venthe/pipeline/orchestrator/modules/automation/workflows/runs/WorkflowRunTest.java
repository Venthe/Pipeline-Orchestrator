package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.definition.WorkflowDefinition;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;

@Slf4j
class WorkflowRunTest {
    private static final OffsetDateTime EXAMPLE_START_DATE = OffsetDateTime.parse("2023-10-31T01:30+01:00");

    @Test
    void name() {
        var triggeringEntity = Mockito.mock(TriggeringEntity.class);
        var workflowDefinition = Mockito.mock(WorkflowDefinition.class);
        var timeService = Mockito.mock(TimeService.class);
        var timeServiceZone = Mockito.mock(TimeService.Offset.class);
        Mockito.when(timeService.zone()).thenReturn(timeServiceZone);
        Mockito.when(timeServiceZone.now()).thenReturn(EXAMPLE_START_DATE);


        var run = new WorkflowRun(workflowDefinition, timeService, triggeringEntity);
        log.info("{}", run);

        Assertions.assertThat(run.getId()).isNotNull();
        Assertions.assertThat(run.getStartDate()).isEqualTo(EXAMPLE_START_DATE);
        Assertions.assertThat(run.getEndDate()).isEmpty();
        Assertions.assertThat(run.getStatus()).isEqualTo(WorkflowExecutionStatus.REQUESTED);
    }
}
