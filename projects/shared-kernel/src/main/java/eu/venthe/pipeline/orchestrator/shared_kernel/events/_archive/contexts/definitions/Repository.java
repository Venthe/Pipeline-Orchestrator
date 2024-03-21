package eu.venthe.pipeline.orchestrator.shared_kernel.events._archive.contexts.definitions;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Optional;

public interface Repository extends RepositoryExtension {
    // /**
    //  * Unique identifier of the repository
    //  */
    // default Integer getId() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The GraphQL identifier of the repository.
    //  */
    // default String getNodeId() {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * The name of the repository.
     */
    String getName();

    // /**
    //  * The full, globally unique, name of the repository.
    //  */
    // default String getFullName() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether the repository is private or public.
    //  */
    // default Boolean getPrivate() {
    //     throw new UnsupportedOperationException();
    // }

    // default User getOwner() {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * The URL to view the repository on <system>.
     */
    default URI getHtmlUrl() {
        throw new UnsupportedOperationException();
    }

    // /**
    //  * The repository description.
    //  */
    // default Optional<String> getDescription() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether the repository is a fork.
    //  */
    // default Boolean getFork() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The URL to get more information about the repository from the System API.
    //  */
    // default URI getUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the forks of the repository.
    //  */
    // default URI getForksUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about deploy keys on the repository.
    //  */
    // default UriTemplate getKeysUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about collaborators of the repository.
    //  */
    // default UriTemplate getCollaboratorsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the teams on the repository.
    //  */
    // default URI getTeamsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the hooks on the repository.
    //  */
    // default URI getHooksUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about issue events on the repository.
    //  */
    // default UriTemplate getIssueEventsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the events of the repository.
    //  */
    // default URI getEventsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to list the available assignees for issues in the repository.
    //  */
    // default UriTemplate getAssigneesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about branches in the repository.
    //  */
    // default UriTemplate getBranchesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to get information about tags on the repository.
    //  */
    // default URI getTagsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * A template for the API URL to create or retrieve a raw Git blob in the repository.
     */
    default UriTemplate getBlobsUrl() {
        throw new UnsupportedOperationException();
    }

    // /**
    //  * A template for the API URL to get information about Git tags of the repository.
    //  */
    // default UriTemplate getGitTagsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about Git refs of the repository.
    //  */
    // default UriTemplate getGitRefsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to create or retrieve a raw Git tree of the repository.
    //  */
    // default UriTemplate getTreesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about statuses of a commit.
    //  */
    // default UriTemplate getStatusesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to get information about the languages of the repository.
    //  */
    // default URI getLanguagesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the stargazers on the repository.
    //  */
    // default URI getStargazersUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to list the contributors to the repository.
    //  */
    // default URI getContributorsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the subscribers on the repository.
    //  */
    // default URI getSubscribersUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to subscribe to notifications for this repository.
    //  */
    // default URI getSubscriptionUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about commits on the repository.
    //  */
    // default UriTemplate getCommitsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about Git commits of the repository.
    //  */
    // default UriTemplate getGitCommitsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about comments on the repository.
    //  */
    // default UriTemplate getCommentsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about issue comments on the repository.
    //  */
    // default UriTemplate getIssueCommentUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get the contents of the repository.
    //  */
    // default UriTemplate getContentsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to compare two commits or refs.
    //  */
    // default UriTemplate getCompareUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to merge branches in the repository.
    //  */
    // default URI getMergesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to download the repository as an archive.
    //  */
    // default UriTemplate getArchiveUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the downloads on the repository.
    //  */
    // default URI getDownloadsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about issues on the repository.
    //  */
    // default UriTemplate getIssuesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about pull requests on the repository.
    //  */
    // default UriTemplate getPullsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about milestones of the repository.
    //  */
    // default UriTemplate getMilestonesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about notifications on the repository.
    //  */
    // default UriTemplate getNotificationsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about labels of the repository.
    //  */
    // default UriTemplate getLabelsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * A template for the API URL to get information about releases on the repository.
    //  */
    // default UriTemplate getReleasesUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * The API URL to list the deployments of the repository.
    //  */
    // default URI getDeploymentsUrl() {
    //     throw new UnsupportedOperationException();
    // }

    OffsetDateTime getCreatedAt();

    OffsetDateTime getUpdatedAt();

    Optional<OffsetDateTime> getPushedAt();

    URI getGitUrl();

    String getSshUrl();

    URI getCloneUrl();

    // default URI getSvnUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getHomepage() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getSize() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getStargazersCount() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getWatchersCount() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getLanguage() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether issues are enabled.
    //  */
    // default Boolean getHasIssues() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether projects are enabled.
    //  */
    // default Boolean getHasProjects() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether downloads are enabled.
    //  */
    // default Boolean getHasDownloads() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether the wiki is enabled.
    //  */
    // default Boolean getHasWiki() {
    //     throw new UnsupportedOperationException();
    // }

    // default Boolean getHasPages() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether discussions are enabled.
    //  */
    // default Optional<Boolean> getHasDiscussions() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getForksCount() {
    //     throw new UnsupportedOperationException();
    // }

    // default URI getMirrorUrl() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether the repository is archived.
    //  */
    // default Boolean getArchived() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Returns whether or not this repository is disabled.
    //  */
    // default Optional<Boolean> getDisabled() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getOpenIssuesCount() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<License> getLicense() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getForks() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getOpenIssues() {
    //     throw new UnsupportedOperationException();
    // }

    // default Integer getWatchers() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<Integer> getStargazers() {
    //     throw new UnsupportedOperationException();
    // }

    /**
     * The default branch of the repository.
     */
    String getDefaultBranch();

    // /**
    //  * Whether to allow squash merges for pull requests.
    //  */
    // default Optional<Boolean> getAllowSquashMerge() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether to allow merge commits for pull requests.
    //  */
    // default Optional<Boolean> getAllowMergeCommit() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether to allow rebase merges for pull requests.
    //  */
    // default Optional<Boolean> getAllowRebaseMerge() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether to allow auto-merge for pull requests.
    //  */
    // default Optional<Boolean> getAllowAutoMerge() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether to allow private forks
    //  */
    // default Optional<Boolean> getAllowForking() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<Boolean> getAllowUpdateBranch() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<Boolean> getUseSquashPrTitleAsDefault() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getSquashMergeCommitMessage() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getSquashMergeCommitTitle() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getMergeCommitMessage() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getMergeCommitTitle() {
    //     throw new UnsupportedOperationException();
    // }

    // default Boolean getIsTemplate() {
    //     throw new UnsupportedOperationException();
    // }

    // default Boolean getWebCommitSignoffRequired() {
    //     throw new UnsupportedOperationException();
    // }

    // default Set<String> getTopics() {
    //     throw new UnsupportedOperationException();
    // }

    // default Visibility getVisibility() {
    //     throw new UnsupportedOperationException();
    // }

    // /**
    //  * Whether to delete head branches when pull requests are merged
    //  */
    // default Optional<Boolean> getDeleteBranchOnMerge() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getMasterBranch() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<Permissions> getPermissions() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<Boolean> getPublic() {
    //     throw new UnsupportedOperationException();
    // }

    // default Optional<String> getOrganization() {
    //     throw new UnsupportedOperationException();
    // }

    @RequiredArgsConstructor
    enum Visibility {
        PUBLIC("public"),
        PRIVATE("private"),
        INTERNAL("internal");

        private final String value;
    }

    interface Permissions {
        default Boolean getPull() {
            throw new UnsupportedOperationException();
        }

        default Boolean getPush() {
            throw new UnsupportedOperationException();
        }

        default Boolean getAdmin() {
            throw new UnsupportedOperationException();
        }

        default Optional<Boolean> getMaintain() {
            throw new UnsupportedOperationException();
        }

        default Optional<Boolean> getTriage() {
            throw new UnsupportedOperationException();
        }
    }
}
