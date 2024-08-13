package eu.venthe.platform.shared_kernel.git;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GitReference {
    GitHash referencedHash;
    Name name;

    public static GitReference of(String hash, String ref) {
        return new GitReference(GitHash.of(hash), Name.of(ref));
    }

    @Value(staticConstructor = "of")
    public static class Name {
        String value;
        String shortName;
        String type;

        public static Name of(String ref) {
            throw new UnsupportedOperationException();
        }
    }
}
