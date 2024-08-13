package eu.venthe.pipeline.workflow.runs.dependencies;

import java.time.OffsetDateTime;

public interface TimeService {
    Offset offset();

    interface Offset {
        OffsetDateTime now();
    }
}
