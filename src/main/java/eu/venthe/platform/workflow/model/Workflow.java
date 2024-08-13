package eu.venthe.platform.workflow.model;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.nio.file.Path;
import java.time.OffsetDateTime;

/**
 * Aggregates a single workflow file across the branches as an available workflow
 */
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Workflow {
    @EqualsAndHashCode.Include
    private final Path file;
    // TODO: Update name
    // TODO: Set name
    //  eiter filename or if present workflow name
    private String name;
    private boolean pinnned = false;
    private boolean enabled = true;
    private OffsetDateTime lastRan;

    public void pin() {
        pinnned = true;
    }

    public void unpin() {
        pinnned = false;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void getTotalNumberOfRuns() {
        throw new UnsupportedOperationException();
    }

    public void triggerWorkflow() {
        throw new UnsupportedOperationException();
    }

    public boolean isPinned() {
        return pinnned;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
