package eu.venthe.pipeline.task_scheduler.api;

public interface TaskSchedulerCommandService {
    void retriggerScheduledTask();
    void executeScheduledTask();
    void scheduleTask();
    void unregisterScheduledTask();
}
