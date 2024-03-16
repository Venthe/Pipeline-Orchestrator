package eu.venthe.pipeline.orchestrator.task_scheduler.api;

public interface TaskSchedulerCommandService {
    void retriggerScheduledTask();
    void executeScheduledTask();
    void scheduleTask();
    void unregisterScheduledTask();
}
