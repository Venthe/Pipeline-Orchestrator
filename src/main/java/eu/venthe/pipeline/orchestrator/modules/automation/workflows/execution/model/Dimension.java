package eu.venthe.pipeline.orchestrator.modules.automation.workflows.execution.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;

@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class Dimension implements Map.Entry<String, String> {
    private final String key;
    private final String value;

    public Dimension(Map.Entry<String, String> e) {
        this.key = e.getKey();
        this.value = e.getValue();
    }

    @Override
    public String setValue(String value) {
        throw new UnsupportedOperationException();
    }

    public interface Value {
        Dimension getValue();
    }
}
