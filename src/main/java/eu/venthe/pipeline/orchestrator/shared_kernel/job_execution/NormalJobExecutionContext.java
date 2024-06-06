package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts.*;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.contexts.utilities.ContextUtilities;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
@SuppressWarnings("ALL")
public class NormalJobExecutionContext extends CommonJobContext implements JobContext_ {

    public NormalJobExecutionContext(JsonNode _root) {
        super(_root);
    }
}
