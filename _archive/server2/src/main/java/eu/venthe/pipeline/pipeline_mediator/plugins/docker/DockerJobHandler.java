package eu.venthe.pipeline.pipeline_mediator.plugins.docker;

import com.github.dockerjava.api.DockerClient;
import eu.venthe.pipeline.pipeline_mediator.domain.plugins.JobHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DockerJobHandler implements JobHandler {

    private final DockerClient client;

    @Override
    public void run(String referenceId) {
    }

    @Override
    public void halt(String referenceId) {

    }
}
