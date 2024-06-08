package eu.venthe.pipeline.orchestrator.job_executor.adapters.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerDimensions;
import eu.venthe.pipeline.orchestrator.job_executor.application.runner.RunnerId;
import eu.venthe.pipeline.orchestrator.job_executor.domain.model.ExecutionId;
import eu.venthe.pipeline.orchestrator.projects.projects.domain.model.ProjectId;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Value
@RequiredArgsConstructor
public class DockerJobExecutorAdapterInstance implements JobExecutorAdapter.AdapterInstance {
    DockerClient dockerClient;

    @Override
    public void queueJobExecution(ProjectId projectId,
                                  ExecutionId executionId,
                                  URL systemApiUrl,
                                  JobExecutorAdapter.CallbackToken callbackToken,
                                  RunnerDimensions dimensions) {
        try (CreateContainerCmd containerCmd = dockerClient.createContainerCmd("docker.home.arpa/venthe/ubuntu-runner:23.10")) {
            CreateContainerResponse exec = containerCmd
                    .withEnv(Stream.<String>builder()
                            .add("__SETUP_CALLBACK_TOKEN=" + callbackToken.value())
                            .add("__SETUP_SYSTEM_API_URL=" + systemApiUrl.toString())
                            .add("__SETUP_EXECUTION_ID=" + executionId.value())
                            .add("__SETUP_PROJECT_ID=" + projectId.serialize())
                            .build()
                            .toArray(String[]::new)
                    )
                    .withTty(true)
                    .withLabels(Map.of("project_id", projectId.serialize()))
                    .withLabels(Map.of("execution_id", executionId.value()))
                    .withName("action_runner-" + UUID.randomUUID())
                    .exec();
            dockerClient.startContainerCmd(exec.getId()).exec();
        }
    }

    @Override
    public RunnerId registerRunner(RunnerDimensions.Dimension... dimensions) {
        // FIXME: !!!
        return new RunnerId(UUID.randomUUID().toString());
    }
}
