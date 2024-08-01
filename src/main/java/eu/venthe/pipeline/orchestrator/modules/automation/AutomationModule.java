package eu.venthe.pipeline.orchestrator.modules.automation;

import eu.venthe.pipeline.orchestrator.modules.ProjectModule;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.EventHandlerProvider;
import eu.venthe.pipeline.orchestrator.projects.domain.ProjectId;
import eu.venthe.pipeline.orchestrator.shared_kernel.git.Revision;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.ProjectEvent;
import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.util.NamedFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class AutomationModule implements ProjectModule {
    private final FeatureManager featureManager;
    private final EventHandlerProvider eventHandlerProvider;

    @Override
    public void handleEvent(final SystemEvent event) {
        if (!(event instanceof ProjectEvent)) return;

        eventHandlerProvider.handle((ProjectEvent) event);
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

   /*
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
    */
}
