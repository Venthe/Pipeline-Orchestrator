package eu.venthe.pipeline.orchestrator.job_executor.domain.model;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;
import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.model.AdapterId;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class AdapterInstanceAggregate implements Aggregate<AdapterId> {
    AdapterId id;
    JobExecutorAdapter.AdapterInstance adapterInstance;
}
