package eu.venthe.pipeline.orchestrator.modules.workflow.services;

import eu.venthe.pipeline.orchestrator.modules.workflow.domain.model.JobExecutionId;

import java.io.File;
import java.nio.file.Path;

public class WorkflowModuleCommandService {
    public void registerManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    public void unregisterManualWorkflow(Path workflowFilename) {
        throw new UnsupportedOperationException();
    }

    public JobExecutionId executeManualWorkflow(String ref, File workflowFile) {
        throw new UnsupportedOperationException();
    }
}
