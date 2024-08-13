package eu.venthe.pipeline.runner.runner_engine.template.model.dimensions;

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
