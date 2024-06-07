package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
@SuppressWarnings("ALL")
public class NormalJobExecutionExecutionContext extends CommonJobExecutionContext implements JobExecutionContext {

    public NormalJobExecutionExecutionContext(JsonNode _root) {
        super(_root);
    }
}
