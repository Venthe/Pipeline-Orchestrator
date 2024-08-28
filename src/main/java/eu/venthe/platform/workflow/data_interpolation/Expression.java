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
    @Singular
    Set<ContextKey> allowedKeys = Collections.emptySet();
    @Singular
    Set<ExpressionFunction> allowedFunctions = Collections.emptySet();
}
