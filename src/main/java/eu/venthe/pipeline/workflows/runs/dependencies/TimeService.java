package eu.venthe.pipeline.workflows.runs.dependencies;

import java.time.OffsetDateTime;

public interface TimeService {
    Offset offset();

    interface Offset {
        OffsetDateTime now();
    }
}
