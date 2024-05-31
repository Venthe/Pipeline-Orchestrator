package eu.venthe.pipeline.orchestrator.projects.projects.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface WorkflowExecution {
    void handleEvent(SystemEvent event);
    void executeManualWorkflow(String workflow);
}
