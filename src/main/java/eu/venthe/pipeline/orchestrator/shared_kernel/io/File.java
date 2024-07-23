package eu.venthe.pipeline.orchestrator.shared_kernel.io;

public record File(byte[] content,
                   Metadata metadata) {
}
