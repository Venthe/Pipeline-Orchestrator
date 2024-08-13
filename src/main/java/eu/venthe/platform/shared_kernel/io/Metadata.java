package eu.venthe.platform.shared_kernel.io;

import java.time.Instant;

public record Metadata(
        String filename,
        long size,
        Instant creationTime,
        Instant lastModifiedTime,
        boolean isReadable,
        boolean isWritable,
        boolean isExecutable
) {
}