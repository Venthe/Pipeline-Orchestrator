package eu.venthe.pipeline.orchestrator.shared_kernel.io;

import java.io.OutputStream;

public record File(OutputStream content,
                   Metadata metadata) {
}
