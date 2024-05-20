package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import eu.venthe.pipeline.orchestrator.projects_source.domain.ProjectStatus;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.FileDto;
import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.model.ProjectDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;

class GerritPluginInstanceTest {
    static GerritConfiguration CONFIGURATION = GerritConfiguration.builder()
            .basePath("http://localhost:15480")
            .username("admin")
            .password("secret")
            .build();

    ProjectSourcePlugin.PluginInstance plugin = new GerritPluginInstance(CONFIGURATION);

    @Test
    void listProjects() {
        Collection<ProjectDto> projects = plugin.getProjects();

        Assertions.assertThat(projects).hasSize(2)
                .containsExactlyInAnyOrder(
                        new ProjectDto("All-Projects", ProjectStatus.ACTIVE),
                        new ProjectDto("All-Users", ProjectStatus.ACTIVE)
                );
    }

    @Test
    void listFiles() {
        Collection<FileDto> files = plugin.getFileList("All-Projects", "refs/meta/config", Paths.get("."));

        Assertions.assertThat(files).hasSize(3)
                .containsExactlyInAnyOrder(
                        new FileDto(Paths.get("project.config"), 2223L, FileDto.Type.FILE),
                        new FileDto(Paths.get(".git"), 4096L, FileDto.Type.DIRECTORY),
                        new FileDto(Paths.get("groups"), 336L, FileDto.Type.FILE)
                );
    }

    @Test
    void showFile() {
        Optional<String> file = plugin.getFile("All-Projects", "refs/meta/config", Paths.get("project.config")).map(e -> new String(e, StandardCharsets.UTF_8));

        Assertions.assertThat(file).isPresent().hasValue("""
                [project]
                	description = Access inherited by all other projects.
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
                	create = group Project Owners
                	editTopicName = +force group Administrators
                	editTopicName = +force group Project Owners
                	forgeAuthor = group Registered Users
                	forgeCommitter = group Administrators
                	forgeCommitter = group Project Owners
                	label-Code-Review = -2..+2 group Administrators
                	label-Code-Review = -2..+2 group Project Owners
                	label-Code-Review = -1..+1 group Registered Users
                	push = group Administrators
                	push = group Project Owners
                	read = group Anonymous Users
                	revert = group Registered Users
                	submit = group Administrators
                	submit = group Project Owners
                [access "refs/meta/config"]
                	exclusiveGroupPermissions = read
                	create = group Administrators
                	create = group Project Owners
                	label-Code-Review = -2..+2 group Administrators
                	label-Code-Review = -2..+2 group Project Owners
                	push = group Administrators
                	push = group Project Owners
                	read = group Administrators
                	read = group Project Owners
                	submit = group Administrators
                	submit = group Project Owners
                [access "refs/meta/version"]
                	read = group Anonymous Users
                [access "refs/tags/*"]
                	create = group Administrators
                	create = group Project Owners
                	createSignedTag = group Administrators
                	createSignedTag = group Project Owners
                	createTag = group Administrators
                	createTag = group Project Owners
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
