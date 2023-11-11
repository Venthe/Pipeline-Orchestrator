package eu.venthe.pipeline.pipeline_mediator.plugins.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DockerConfig {
    @Bean
    DockerClientConfig localConfiguration() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    }

    DockerClientConfig remoteConfiguration() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://docker.somewhere.tld:2376")
                .withDockerTlsVerify(true)
                .withDockerCertPath("/home/user/.docker")
                .withRegistryUsername("registryUser")
                .withRegistryPassword("registryPass")
                .withRegistryEmail("registryMail")
                .withRegistryUrl("registryUrl")
                .build();
    }

    @Bean
    DockerHttpClient dockerHttpClient(DockerClientConfig config) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
    }

    @Bean
    DockerClient dockerClient(DockerClientConfig config, DockerHttpClient httpClient) {
        return DockerClientImpl.getInstance(config, httpClient);
    }
}
