package eu.venthe.pipeline.orchestrator.plugins;

import lombok.Data;
import lombok.Value;

@Data
public class Project {
    private Status status;
    private Id id;

    public enum Status {ACTIVE}

    @Value
    public static class Id {
        String systemId;
        String id1;
    }
}
