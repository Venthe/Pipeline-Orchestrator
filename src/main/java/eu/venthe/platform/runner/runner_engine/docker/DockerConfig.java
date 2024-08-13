package eu.venthe.platform.runner.runner_engine.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.transport.DockerHttpClient;
import com.github.dockerjava.zerodep.ZerodepDockerHttpClient;

import java.time.Duration;

public class DockerConfig {

    public static DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();
    }

    public static DockerHttpClient dockerHttpClient(DockerClientConfig config) {
        return new ZerodepDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(100))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
    }

    public static DockerClient dockerClient(DockerClientConfig config, DockerHttpClient httpClient) {
        return DockerClientBuilder.getInstance(config)
                .withDockerHttpClient(httpClient)
                .build();
    }
}
