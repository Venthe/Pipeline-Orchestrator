package eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.JobExecutorCallbackService;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.infrastructure.WorkflowRunRepository;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.JobExecutionContext;
import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.jobs.run_context.JobRunContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobExecutorCallbackServiceImpl implements JobExecutorCallbackService {
    private final WorkflowRunRepository repository;

    @Override
    public JobRunContext requestContext(final JobCallbackCallMetadata callMetadata) {
        var aggregate = repository.find(new WorkflowRunRepository.Id(callMetadata.projectId(), callMetadata.workflowRunId())).map(a->a.run()).orElseThrow();
        var context = aggregate.provideContext(callMetadata.jobRunId());
        return context;
    }
}
