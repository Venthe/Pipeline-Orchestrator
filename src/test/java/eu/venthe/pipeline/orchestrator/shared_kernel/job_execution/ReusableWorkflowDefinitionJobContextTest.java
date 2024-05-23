package eu.venthe.pipeline.orchestrator.shared_kernel.job_execution;

import com.fasterxml.jackson.databind.JsonNode;
import eu.venthe.pipeline.orchestrator.shared_kernel.job_execution.contexts.AbstractContextTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import java.io.StringReader;

class ReusableWorkflowDefinitionJobContextTest extends AbstractContextTest {

    @Test
    void test() {
        // Given
        JsonNode expected = readTree("""
                {
                  "github": {
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
                  },
                  "env": {
                    "first_name": "Mona",
                    "super_duper_var": "totally_awesome"
                  },
                  "vars": {
                    "mascot": "Mona"
                  },
                  "job": {
                    "status": "success",
                    "container": {
                      "network": "github_network_53269bd575974817b43f4733536b200c"
                    },
                    "services": {
                      "postgres": {
                        "id": "60972d9aa486605e66b0dad4abb638dc3d9116f566579e418166eedb8abb9105",
                        "ports": {
                          "5432": "49153"
                        },
                        "network": "github_network_53269bd575974817b43f4733536b200c"
                      }
                    }
                  },
                  "jobs": {
                    "example_job": {
                      "result": "success",
                      "outputs": {
                        "output1": "hello",
                        "output2": "world"
                      }
                    }
                  },
                  "steps": {
                    "checkout": {
                      "outputs": {},
                      "outcome": "success",
                      "conclusion": "success"
                    },
                    "generate_number": {
                      "outputs": {
                        "random_number": "1"
                      },
                      "outcome": "success",
                      "conclusion": "success"
                    }
                  },
                  "runner": {
                    "os": "Linux",
                    "arch": "X64",
                    "name": "GitHub Actions 2",
                    "tool_cache": "/opt/hostedtoolcache",
                    "temp": "/home/runner/work/_temp"
                  },
                  "secrets": {
                    "GITHUB_TOKEN": "***",
                    "NPM_TOKEN": "***",
                    "SUPERSECRET": "***"
                  },
                  "strategy": {
                    "fail_fast": true,
                    "job_index": 3,
                    "job_total": 4,
                    "max_parallel": 4
                  },
                  "matrix": {
                    "os": "ubuntu-latest",
                    "node": 16
                  },
                  "needs": {
                    "build": {
                      "result": "success",
                      "outputs": {
                        "build_id": "123456"
                      }
                    },
                    "deploy": {
                      "result": "failure",
                      "outputs": {}
                    }
                  },
                  "inputs": {
                    "build_id": 123456768,
                    "deploy_target": "deployment_sys_1a",
                    "perform_deploy": true
                  }
                }
                """);

        // When
        JsonNode result = objectMapper.valueToTree(new ReusableWorkflowJobContext(expected));

        // Then
        Assertions.assertEquals(expected, result);
//        Assertions.assertThat(result)/*.withFailMessage(() -> getString(expected, result))*/.isEqualTo(expected);
    }

    @SneakyThrows
    private static String getString(JsonNode expected, JsonNode result) {
        JsonObject source = getJsonObject(expected);
        JsonObject target = getJsonObject(result);
        JsonPatch diff = Json.createDiff(source, target);
        return objectMapper.readTree(diff.toString()).toPrettyString();
    }

    private static JsonObject getJsonObject(JsonNode result) {
        return Json.createReader(new StringReader(result.toPrettyString())).readValue().asJsonObject();
    }
}
