package eu.venthe.platform.workflow.data_interpolation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContextKey {
    SYSTEM("system"),
    VARS("vars"),
    INPUT("input");

    private final String input;
}
