package eu.venthe.pipeline.orchestrator.workflow_executions.domain.job_executions.application.runner;

import lombok.Builder;
import lombok.Singular;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Builder
public record RunnerDimensions(@Singular Map<String, String> dimensions) {
    public RunnerDimensions {
        dimensions = new HashMap<>();
    }

    public String put(String key, String value) {
        return dimensions.put(key, value);
    }

    public Stream<Map.Entry<String, String>> stream() {
        return dimensions.entrySet().stream();
    }

    public static class RunnerDimensionsBuilder {
        public RunnerDimensionsBuilder dimension(Dimension.Value dimensionValue) {
            this.dimension(dimensionValue.getValue().getKey(), dimensionValue.getValue().getValue());
            return this;
        }

        public RunnerDimensionsBuilder dimension(Dimension dimension) {
            this.dimension(dimension.getKey(), dimension.getValue());
            return this;
        }
    }
}
