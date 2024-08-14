package eu.venthe.platform.namespace.domain;

public record NamespaceName(String value) {
    public static final NamespaceName DEFAULT = new NamespaceName("DEFAULT");
}
