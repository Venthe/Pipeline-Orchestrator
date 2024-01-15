package eu.venthe.pipeline.orchestrator.events.contexts.definitions;

import java.time.OffsetDateTime;
import java.util.Set;

public interface Commit extends CommitExtension {
    // default String getId() {
    //     throw new UnsupportedOperationException();
    // }

    // default String getTreeId() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    // * Whether this commit is distinct from any that have been pushed before.
    // */
    // default Boolean getDistinct(); {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * The commit message.
     */
    String getMessage();

    /**
     * The ISO 8601 timestamp of the commit.
     */
    OffsetDateTime getTimestamp();

    // /**
    //  * URL that points to the commit API resource.
    //  */
    // default URI getUrl() {
    //     throw new UnsupportedOperationException();
    // }

    Committer getAuthor();

    Committer getCommitter();

    /**
     * An array of files added in the commit. For extremely large commits where GitHub is unable to calculate this list in a timely manner, this may be empty even if files were added.
     */
    Set<String> getAdded();

    /**
     * An array of files modified by the commit. For extremely large commits where GitHub is unable to calculate this list in a timely manner, this may be empty even if files were modified.
     */
    Set<String> getModified();

    /**
     * An array of files removed in the commit. For extremely large commits where GitHub is unable to calculate this list in a timely manner, this may be empty even if files were removed.
     */
    Set<String> getRemoved();
}
