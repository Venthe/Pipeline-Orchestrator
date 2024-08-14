package eu.venthe.platform.runner.runner_engine.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import eu.venthe.platform.runner.runner_engine.template.RunnerEngineInstance;
import eu.venthe.platform.runner.runner_engine.template.model.RunCallbackToken;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerContext;
import eu.venthe.platform.runner.runner_engine.template.model.RunnerId;
import eu.venthe.platform.runner.runner_engine.template.model.dimensions.RunnerDimensions;
import eu.venthe.platform.workflow.model.JobRunId;
import eu.venthe.platform.workflow.runs.WorkflowRunId;
import eu.venthe.platform.project.domain.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Value
@RequiredArgsConstructor
public class DockerEngineInstance implements RunnerEngineInstance {
    DockerClient dockerClient;

    @Override
    public boolean queueExecution(RunnerContext context,
                                  RunnerDimensions dimensions) {
        try (CreateContainerCmd containerCmd = dockerClient.createContainerCmd("docker.home.arpa/venthe/ubuntu-runner:23.10")) {
            CreateContainerResponse exec = containerCmd
                    .withEnv(Stream.<String>builder()
                            .add("__SETUP_CALLBACK_TOKEN=" + context.runCallbackToken().value())
                            .add("__SETUP_SYSTEM_API_URL=" + context.systemApiUrl().toString())
                            // .add("__SETUP_EXECUTION_ID=" + executionId.value())
                            // .add("__SETUP_PROJECT_ID=" + projectId.serialize())
                            .build()
                            .toArray(String[]::new)
                    )
                    .withTty(true)
                    // .withLabels(Map.of("project_id", projectId.serialize()))
                    // .withLabels(Map.of("execution_id", Integer.valueOf(executionId.value()).toString()))
                    .withName("action_runner-" + UUID.randomUUID())
                    .exec();
            dockerClient.startContainerCmd(exec.getId()).exec();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public RunnerId registerRunner(RunnerDimensions dimensions) {
        // FIXME: !!!
        return new RunnerId(UUID.randomUUID().toString());
    }
}
