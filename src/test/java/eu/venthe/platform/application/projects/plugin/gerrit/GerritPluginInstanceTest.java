/*
package eu.venthe.platform.application.repositorys.plugin.gerrit;

import eu.venthe.platform.shared_kernel.repository.RepositoryStatus;
import eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritConfiguration;
import eu.venthe.platform.source_configuration.domain.plugins.gerrit.GerritPluginInstance;
import eu.venthe.platform.source_configuration.domain.plugins.template.RepositorySourcePluginInstance;
import eu.venthe.platform.source_configuration.domain.plugins.template.Repository;
import eu.venthe.platform.source_configuration.domain.model.SourceType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

class GerritPluginInstanceTest {
    static GerritConfiguration CONFIGURATION = GerritConfiguration.builder()
            .basePath("http://localhost:15480")
            .username("admin")
            .password("secret")
            .build();

    RepositorySourcePluginInstance plugin = new GerritPluginInstance(CONFIGURATION);

    @Test
    void listRepository() {
        Collection<Repository> repositorys = plugin.getRepository().collect(Collectors.toSet());

        Assertions.assertThat(repositorys).hasSize(2)
                .containsExactlyInAnyOrder(
                        new Repository("All-Repository", RepositoryStatus.ACTIVE),
                        new Repository("All-Users", RepositoryStatus.ACTIVE)
                );
    }

*/
/*    @Test
    void listFiles() {
        Collection<Metadata> files = plugin.getFileList("All-Repository", "refs/meta/config", Paths.get("."));

        Assertions.assertThat(files).hasSize(3)
                .containsExactlyInAnyOrder(
                        Metadata.directory(Paths.get("repository.config"), 2223L, FileDto.Type.FILE),
                        new Metadata(Paths.get(".git"), 4096L, FileDto.Type.DIRECTORY),
                        new Metadata(Paths.get("groups"), 336L, FileDto.Type.FILE)
                );
    }*//*


    @Test
    void showFile() {
        Optional<String> file = plugin.getFile("All-Repository", "refs/meta/config", Paths.get("repository.config")).map(e -> new String(e, StandardCharsets.UTF_8));

        Assertions.assertThat(file).isPresent().hasValue("""
                [repository]
                	description = Access inherited by all other repositorys.
                [receive]
                	requireContributorAgreement = false
                	requireSignedOffBy = false
                	requireChangeId = true
                	enableSignedPush = false
                [submit]
                	mergeContent = true
                [capability]
                	administrateServer = group Administrators
                	priority = batch group Service Users
                	streamEvents = group Service Users
                [access "refs/*"]
                	read = group Administrators
                [access "refs/for/*"]
                	addPatchSet = group Registered Users
                [access "refs/for/refs/*"]
                	push = group Registered Users
                	pushMerge = group Registered Users
                [access "refs/heads/*"]
                	create = group Administrators
                	create = group Repository Owners
                	editTopicName = +force group Administrators
                	editTopicName = +force group Repository Owners
                	forgeAuthor = group Registered Users
                	forgeCommitter = group Administrators
                	forgeCommitter = group Repository Owners
                	label-Code-Review = -2..+2 group Administrators
                	label-Code-Review = -2..+2 group Repository Owners
                	label-Code-Review = -1..+1 group Registered Users
                	push = group Administrators
                	push = group Repository Owners
                	read = group Anonymous Users
                	revert = group Registered Users
                	submit = group Administrators
                	submit = group Repository Owners
                [access "refs/meta/config"]
                	exclusiveGroupPermissions = read
                	create = group Administrators
                	create = group Repository Owners
                	label-Code-Review = -2..+2 group Administrators
                	label-Code-Review = -2..+2 group Repository Owners
                	push = group Administrators
                	push = group Repository Owners
                	read = group Administrators
                	read = group Repository Owners
                	submit = group Administrators
                	submit = group Repository Owners
                [access "refs/meta/version"]
                	read = group Anonymous Users
                [access "refs/tags/*"]
                	create = group Administrators
                	create = group Repository Owners
                	createSignedTag = group Administrators
                	createSignedTag = group Repository Owners
                	createTag = group Administrators
                	createTag = group Repository Owners
                [label "Code-Review"]
                	function = MaxWithBlock
                	defaultValue = 0
                	value = -2 This shall not be submitted
                	value = -1 I would prefer this is not submitted as is
                	value = 0 No score
                	value = +1 Looks good to me, but someone else must approve
                	value = +2 Looks good to me, approved
                	copyCondition = changekind:NO_CHANGE OR changekind:TRIVIAL_REBASE OR is:MIN
                """);
    }
}
*/
