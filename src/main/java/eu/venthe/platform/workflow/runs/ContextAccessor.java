package eu.venthe.platform.workflow.runs;

import java.util.Set;

public interface ContextAccessor extends Substitution {
    Substitution prepare(Set<Path> allowedPathPrefixes);

    record Path(String value) {
    }

}
