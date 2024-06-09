package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerImage extends Dimension {
    public ContainerImage(String value) {
        super("container_image", value);
    }
}
