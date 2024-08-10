package eu.venthe.pipeline.orchestrator.shared_kernel.git;

import lombok.Value;

@Value
public class GitRevision {
    FullyFormedName name;
    Hash hash;

    public GitRevision(final String name, final String hash) {
        this.name = new FullyFormedName(name);
        this.hash = new Hash(hash);
    }

    public Type getType() {
        return name.getType();
    }

    public record FullyFormedName(String value) {
        public String shortName() {
            throw new UnsupportedOperationException();
        }

        public Type getType() {
            throw new UnsupportedOperationException();
        }
    }

    public record Hash(String value) {
    }

    public enum Type {
        BRANCH,
        TAG
    }
}
