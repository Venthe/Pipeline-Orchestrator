package eu.venthe.pipeline.pipeline_mediator.domain.model.vo;

public enum EventStatus {
    REGISTERED_FOR_PROCESSING,
    PIPELINE_TRIGGERED,
    RETRY_REQUIRED,
    SKIPPED,
    PIPELINE_FAILED,
    PIPELINE_COMPLETED,
    UNDELIVERABLE;
}
