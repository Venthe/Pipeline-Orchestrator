package eu.venthe.platform.shared_kernel.io;

import java.time.Instant;

public record Metadata(
        String path,
        FileType fileType,
        long size,
        Instant creationTime,
        Instant lastModifiedTime,
        boolean isReadable,
        boolean isWritable,
        boolean isExecutable
) {

    public static Metadata directory(String path,
                                     Instant creationTime,
                                     Instant lastModifiedTime,
                                     boolean isReadable,
                                     boolean isWritable,
                                     boolean isExecutable) {
        return new Metadata(path, FileType.DIRECTORY, 0, creationTime, lastModifiedTime, isReadable, isWritable, isExecutable);
    }

    public enum FileType {
        DIRECTORY,
        FILE
    }
}
