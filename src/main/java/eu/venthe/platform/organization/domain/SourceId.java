package eu.venthe.platform.organization.domain;

public record SourceId(String value) {
    public static SourceId DEFAULT = new SourceId("default");
}
