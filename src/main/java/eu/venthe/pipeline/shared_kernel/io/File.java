package eu.venthe.pipeline.shared_kernel.io;

import java.io.OutputStream;

public record File(OutputStream content,
                   Metadata metadata) {
}
