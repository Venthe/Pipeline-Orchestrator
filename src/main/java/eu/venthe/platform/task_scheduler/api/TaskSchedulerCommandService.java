package eu.venthe.platform.task_scheduler.api;

public interface TaskSchedulerCommandService {
    void retriggerScheduledTask();
    void executeScheduledTask();
    void scheduleTask();
    void unregisterScheduledTask();
}
