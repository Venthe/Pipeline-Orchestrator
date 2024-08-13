package eu.venthe.pipeline.runner.runner_engine.template.model.dimensions;

import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Builder
public record RunnerDimensions(Map<String, String> dimensions) {
    public RunnerDimensions {
        dimensions = new HashMap<>();
    }

    public static RunnerDimensions none() {
        return new RunnerDimensions(new HashMap<>());
    }

    public String put(String key, String value) {
        return dimensions.put(key, value);
    }

    public Stream<Map.Entry<String, String>> stream() {
        return dimensions.entrySet().stream();
    }

    public static class RunnerDimensionsBuilder {
        public RunnerDimensionsBuilder() {
            if (this.dimensions == null) this.dimensions = new HashMap<>();
        }

        public RunnerDimensionsBuilder dimension(Dimension dimension) {
            this.dimensions.put(dimension.getKey(), dimension.getValue());
            return this;
        }

        public RunnerDimensionsBuilder dimension(Dimension.Value dimensionValue) {
            dimension(dimensionValue.getValue());
            return this;
        }

        public RunnerDimensionsBuilder from(Dimension[] dimensions) {
            for (Dimension _dimension : dimensions) {
                dimension(_dimension);
            }
            return this;
        }
    }
}
