package eu.venthe.pipeline.pipeline_mediator.domain.plugins;

public interface  JobHandler {
    void run(String referenceId);
    void halt(String referenceId);
}
