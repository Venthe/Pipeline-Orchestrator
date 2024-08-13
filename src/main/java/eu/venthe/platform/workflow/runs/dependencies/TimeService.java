package eu.venthe.platform.workflow.runs.dependencies;

import java.time.OffsetDateTime;

public interface TimeService {
    Offset offset();

    interface Offset {
        OffsetDateTime now();
    }
}
