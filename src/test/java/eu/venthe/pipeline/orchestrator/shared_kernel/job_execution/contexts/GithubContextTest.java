package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs._archive._1.context.job_execution.contexts.GithubContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GithubContextTest extends AbstractContextTest {

    @Test
    void parsesExampleContext() {
        // given
        JsonNode ghc = readTree("""
                {
                  "token": "***",
                  "job": "dump_contexts_to_log",
                  "ref": "refs/heads/my_branch",
                  "sha": "c27d339ee6075c1f744c5d4b200f7901aad2c369",
                  "repository": "octocat/hello-world",
                  "repository_owner": "octocat",
                  "repository_url": "git://github.com/octocat/hello-world.git",
                  "run_id": "1536140711",
                  "run_number": "314",
                  "retention_days": "90",
                  "run_attempt": "1",
                  "actor": "octocat",
                  "workflow": "Context testing",
                  "head_ref": "",
                  "base_ref": "",
                  "event_name": "push",
                  "event": {},
                  "server_url": "https://github.com",
                  "api_url": "https://api.github.com",
                  "graphql_url": "https://api.github.com/graphql",
                  "ref_name": "my_branch",
                  "ref_protected": false,
                  "ref_type": "branch",
                  "secret_source": "Actions",
                  "workspace": "/home/runner/work/hello-world/hello-world",
                  "action": "github_step",
                  "event_path": "/home/runner/work/_temp/_github_workflow/event.json",
                  "action_repository": "",
                  "action_ref": "",
                  "path": "/home/runner/work/_temp/_runner_file_commands/add_path_b037e7b5-1c88-48e2-bf78-eaaab5e02602",
                  "env": "/home/runner/work/_temp/_runner_file_commands/set_env_b037e7b5-1c88-48e2-bf78-eaaab5e02602"
                }
                """);

        // when
        GithubContext context = GithubContext.ensure(ghc);

        // then
        assertThat(context.getToken()).isEqualTo("***");
        assertThat(context.getJob()).isEqualTo("dump_contexts_to_log");
        assertThat(context.getRef()).isEqualTo("refs/heads/my_branch");
        assertThat(context.getSha()).isEqualTo("c27d339ee6075c1f744c5d4b200f7901aad2c369");
        assertThat(context.getRepository()).isEqualTo("octocat/hello-world");
        assertThat(context.getRepositoryOwner()).isEqualTo("octocat");
        assertThat(context.getRepositoryUrl()).isEqualTo("git://github.com/octocat/hello-world.git");
        assertThat(context.getRunId()).isEqualTo("1536140711");
        assertThat(context.getRunNumber()).isEqualTo("314");
        assertThat(context.getRetentionDays()).isEqualTo("90");
        assertThat(context.getRunAttempt()).isEqualTo("1");
        assertThat(context.getActor()).isEqualTo("octocat");
        assertThat(context.getWorkflow()).isEqualTo("Context testing");
        assertThat(context.getHeadRef()).isEmpty();
        assertThat(context.getBaseRef()).isEmpty();
        assertThat(context.getEventName()).isEqualTo("push");
        assertThat(context.getEvent()).isEqualTo(objectMapper.createObjectNode());
        assertThat(context.getServerUrl()).isEqualTo("https://github.com");
        assertThat(context.getApiUrl()).isEqualTo("https://api.github.com");
        assertThat(context.getGraphqlUrl()).isEqualTo("https://api.github.com/graphql");
        assertThat(context.getRefName()).isEqualTo("my_branch");
        assertThat(context.getRefProtected()).isFalse();
        assertThat(context.getRefType()).isEqualTo("branch");
        assertThat(context.getSecretSource()).isEqualTo("Actions");
        assertThat(context.getWorkspace()).isEqualTo("/home/runner/work/hello-world/hello-world");
        assertThat(context.getAction()).isEqualTo("github_step");
        assertThat(context.getEventPath()).isEqualTo("/home/runner/work/_temp/_github_workflow/event.json");
        assertThat(context.getActionRepository()).isEmpty();
        assertThat(context.getActionRef()).isEmpty();
        assertThat(context.getPath()).isEqualTo("/home/runner/work/_temp/_runner_file_commands/add_path_b037e7b5-1c88-48e2-bf78-eaaab5e02602");
        assertThat(context.getEnv()).isEqualTo("/home/runner/work/_temp/_runner_file_commands/set_env_b037e7b5-1c88-48e2-bf78-eaaab5e02602");
    }
}
