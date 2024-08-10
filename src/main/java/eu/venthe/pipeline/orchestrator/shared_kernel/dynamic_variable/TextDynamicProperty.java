package eu.venthe.pipeline.orchestrator.shared_kernel.dynamic_variable;

public record TextDynamicProperty(String value) implements DynamicProperty {
    @Override
    public boolean isText() {
        return true;
    }

    @Override
    public String asText() {
        return value;
    }
}
