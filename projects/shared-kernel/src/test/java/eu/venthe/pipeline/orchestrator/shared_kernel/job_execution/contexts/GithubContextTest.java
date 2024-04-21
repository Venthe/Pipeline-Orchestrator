package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GithubContextTest {
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup() {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Test
    void parsesExampleContext() {
        // given
        JsonNode ghc = objectMapper.readTree("""
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
        Assertions.assertThat(context)
                .isNotNull()
                .satisfies(ctx -> {
                    Assertions.assertThat(ctx.getToken()).isEqualTo("***");
                });
    }
}
