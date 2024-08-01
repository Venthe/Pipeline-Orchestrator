package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import java.time.OffsetDateTime;

public interface TimeService {
    Offset zone();

    interface Offset {
        OffsetDateTime now();
    }
}
