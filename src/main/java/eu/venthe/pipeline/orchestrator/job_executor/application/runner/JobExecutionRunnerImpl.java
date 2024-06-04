package eu.venthe.pipeline.orchestrator.job_executor.application.runner;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class JobExecutionRunnerImpl implements JobExecutionRunner {
    private final RunnerId runnerId;
    private final ContainerId containerTag;
    private final Map<String, String> dimensions = new HashMap<>();

    public JobExecutionRunnerImpl(RunnerId runnerId,
                                  ContainerId containerTag,
                                  OperatingSystem operatingSystem,
                                  Architecture architecture,
                                  Map.Entry<String, String>... _dimensions) {
        Arrays.stream(_dimensions).forEach(it -> dimensions.put(
                it.getKey().toLowerCase(Locale.ROOT),
                it.getValue()
        ));
        dimensions.put("operating_system", operatingSystem.name());
        dimensions.put("architecture", architecture.name());

        this.runnerId = runnerId;
        this.containerTag = containerTag;
    }
}
