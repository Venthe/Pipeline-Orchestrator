package eu.venthe.pipeline.orchestrator._archive2.plugins.api;

import lombok.*;

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
