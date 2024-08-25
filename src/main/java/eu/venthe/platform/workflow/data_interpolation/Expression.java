package eu.venthe.platform.workflow.data_interpolation;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Collections;
import java.util.Set;

@Builder
@Value
public class Expression<T> {
    T value;
    @Builder.Default
    @Singular
    Set<ContextKey> allowedKeys = Collections.emptySet();
    @Singular
    @Builder.Default
    Set<ExpressionFunction> allowedFunctions = Collections.emptySet();
}
