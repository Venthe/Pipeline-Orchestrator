package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.shared_kernel.dynamic_variable.DynamicProperty;
import org.junit.jupiter.api.Test;

import java.util.Map;

class WorkflowRunTest {
    @Test
    void name() {
        Object organizationId;
        Object repositoryId;
        Object revision;
        /**
         * type: event, id: xxxxxxxx
         * type: workflow_call, id: xxxxxx
         */
        Object correlationId;
        Map<String, DynamicProperty> inputs;
        Map<String, String> organizationVariables;
        Map<String, String> repositoryVariables;

        Object workflowDefinition;
        Object runId;
        Object startDate;
        Object triggeringEntity;
        Object event;

    }
}
