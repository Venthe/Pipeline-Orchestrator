package eu.venthe.pipeline.orchestrator.projects_source.adapter;

import eu.venthe.pipeline.orchestrator.shared_kernel.system_events.SystemEvent;

public interface SystemEventListener {
    void listen(SystemEvent event);
}
