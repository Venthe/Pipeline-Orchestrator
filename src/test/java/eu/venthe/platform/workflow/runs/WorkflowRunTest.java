package eu.venthe.platform.workflow.runs;

import eu.venthe.platform.project.domain.ProjectId;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorkflowRunTest {
    @Test
    void name() {
        Object projectId;
        Object revisionHash;
        Object revisionFullName;

        Object workflowDefinition;
    }

    private record Variables(Map<String, String> ) {
    }
}
