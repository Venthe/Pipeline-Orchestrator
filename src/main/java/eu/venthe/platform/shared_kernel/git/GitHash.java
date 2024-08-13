package eu.venthe.platform.shared_kernel.git;

import lombok.Value;

@Value(staticConstructor = "of")
public class GitHash {
    public static GitHash of(String hash) {
        throw new UnsupportedOperationException();
    }
}
