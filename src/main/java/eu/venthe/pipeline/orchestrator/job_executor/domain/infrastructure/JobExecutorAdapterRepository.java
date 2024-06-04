package eu.venthe.pipeline.orchestrator.job_executor.domain.infrastructure;

import eu.venthe.pipeline.orchestrator.job_executor.adapters.template.JobExecutorAdapter;

public interface JobExecutorAdapterRepository {
    void save(JobExecutorAdapter.AdapterInstance adapterInstance);
}
