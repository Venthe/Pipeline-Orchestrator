package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

//@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Project implements Aggregate<Project.Id> {
    @EqualsAndHashCode.Include
    private final Id id;

    @Value(staticConstructor = "of")
    public static class Id {
        String systemId;
        String id;
    }
}
