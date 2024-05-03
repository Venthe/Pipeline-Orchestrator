package eu.venthe.pipeline.orchestrator.projects_source.adapter.template;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface SystemEventProcessor {
    void process(SystemEvent event);
}
