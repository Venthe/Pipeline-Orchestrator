package eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.version_control_events.contexts.utilities.ContextUtilities;
import lombok.Getter;

// TODO: Implement context url
// TODO: Implement context id
// TODO: Implement context node_id
// TODO: Implement context html_url
// TODO: Implement context diff_url
// TODO: Implement context patch_url
// TODO: Implement context issue_url
// TODO: Implement context commits_url
// TODO: Implement context review_comments_url
// TODO: Implement context review_comment_url
// TODO: Implement context comments_url
// TODO: Implement context statuses_url
// TODO: Implement context number
// TODO: Implement context state
// TODO: Implement context locked
// TODO: Implement context title
// TODO: Implement context user
// TODO: Implement context body
// TODO: Implement context labels
// TODO: Implement context milestone
// TODO: Implement context active_lock_reason
// TODO: Implement context created_at
// TODO: Implement context updated_at
// TODO: Implement context closed_at
// TODO: Implement context merged_at
// TODO: Implement context merge_commit_sha
// TODO: Implement context assignee
// TODO: Implement context assignees
// TODO: Implement context requested_reviewers
// TODO: Implement context requested_teams
// TODO: Implement context head
// TODO: Implement context _links
// TODO: Implement context author_association
// TODO: Implement context auto_merge
// TODO: Implement context draft
// TODO: Implement context merged
// TODO: Implement context mergeable
// TODO: Implement context rebaseable
// TODO: Implement context mergeable_state
// TODO: Implement context merged_by
// TODO: Implement context comments
// TODO: Implement context review_comments
// TODO: Implement context maintainer_can_modify
// TODO: Implement context commits
// TODO: Implement context additions
// TODO: Implement context deletions
// TODO: Implement context changed_files
// components/pull-request.yaml#
@Getter
public class PullRequestContext {
    private final ReferenceInfoContext base;

    protected PullRequestContext(final JsonNode _root) {
        ObjectNode root = ContextUtilities.validateIsObjectNode(_root);

        this.base = ReferenceInfoContext.ensure(root.get("base"));
    }

    public static PullRequestContext ensure(final JsonNode root) {
        return ContextUtilities.ensure(root, PullRequestContext::new);
    }
}
