package eu.venthe.pipeline.orchestrator.organizations.domain.source_configurations.plugins.template.model;

import lombok.Value;

import java.nio.file.Path;

@Value
public class FileDto {
    Path path;
    long size;
    Type type;

    public enum Type {
        FILE,
        DIRECTORY
    }
}
