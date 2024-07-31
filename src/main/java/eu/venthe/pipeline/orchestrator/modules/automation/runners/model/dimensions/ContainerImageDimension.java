package eu.venthe.pipeline.orchestrator.modules.automation.runners.model.dimensions;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerImageDimension extends Dimension {
    public ContainerImageDimension(String value) {
        super("container_image", value);
    }
}
