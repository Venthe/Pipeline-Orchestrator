package eu.venthe.pipeline.orchestrator.modules.workflow.domain.job_executions.context.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@SuppressWarnings("ALL")
public class NormalJobExecutionExecutionContext extends CommonJobExecutionContext implements JobExecutionContext {

    public NormalJobExecutionExecutionContext(JsonNode _root) {
        super(_root);
    }
}
