package eu.venthe.pipeline.orchestrator.projects._projects.domain;

import eu.venthe.pipeline.orchestrator.projects._projects.domain.workflows.Workflow;
import org.springframework.stereotype.Component;

@Component
public class WorkflowFactory {
    public static Workflow fromBytes(byte[] bytes) {
        throw new UnsupportedOperationException();
    }
}
