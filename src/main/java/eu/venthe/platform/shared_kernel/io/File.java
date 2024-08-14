package eu.venthe.platform.shared_kernel.io;

import java.io.InputStream;

public record File(InputStream content,
                   Metadata metadata) {
}
