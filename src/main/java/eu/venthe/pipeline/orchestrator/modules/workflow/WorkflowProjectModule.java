package eu.venthe.pipeline.orchestrator.modules.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import eu.venthe.pipeline.orchestrator.modules.ProjectModule;
import eu.venthe.pipeline.orchestrator.modules.workflow.application.WorkflowExecutionCommandService;
import eu.venthe.pipeline.orchestrator.modules.workflow.domain.workflows.WorkflowDefinition;
import eu.venthe.pipeline.orchestrator.projects.application.ProjectsQueryService;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.WorkflowDispatchEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.model.EventType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.io.*;

@Component
@RequiredArgsConstructor
public class WorkflowProjectModule implements ProjectModule {
    private final FeatureManager featureManager;
    private final WorkflowExecutionCommandService workflowExecutionCommandService;
    private final ProjectsQueryService projectsQueryService;
    private final YAMLMapper yamlMapper;

    @Override
    public void handleEvent(final ProjectId id, final SystemEvent event) {
        if (featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            if (!event.getType().equals(EventType.WORKFLOW_DISPATCH)) throw new UnsupportedOperationException();

            var event1 = (WorkflowDispatchEvent) event;

            var workflow = projectsQueryService.getFile(id, event1.getRevision(), event1.getWorkflow())
                    .map(e -> {
                        try {
                            return new FileInputStream(e);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .map(e -> {
                        try {
                            return readFromInputStream(e);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .map(content -> {
                        try {
                            return yamlMapper.readTree(content);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(e -> new WorkflowDefinition(e, null))
                    .orElseThrow();
            workflowExecutionCommandService.executeWorkflow();

            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerTrackedRevision(final ProjectId id, final Revision ref) {
        if (featureManager.isActive(new NamedFeature("GENERAL_WIP"))) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public void unregisterTrackedRevision(final ProjectId id, final Revision ref) {
        throw new UnsupportedOperationException();
    }

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
