package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model;

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
