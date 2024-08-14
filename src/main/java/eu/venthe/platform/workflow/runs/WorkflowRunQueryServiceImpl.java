package eu.venthe.platform.workflow.runs;

import com.google.common.collect.MoreCollectors;
import eu.venthe.platform.workflow.WorkflowRunQueryService;
import eu.venthe.platform.workflow.runs._archive._1.model.query.WorkflowExecutionDto;
import eu.venthe.platform.workflow.runs.infrastructure.WorkflowRunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkflowRunQueryServiceImpl implements WorkflowRunQueryService {
    private final WorkflowRunRepository repository;

    @Override
    public Optional<WorkflowExecutionDto> getExecutionDetails(final WorkflowRunId executionId) {
        return repository.findAll().stream().filter(e -> e.getId().equals(executionId)).map(e -> toDto(e)).collect(MoreCollectors.toOptional());
    }

    @Override
    public Optional<WorkflowExecutionDto> getExecutionDetails(final WorkflowCorrelationId workflowCorrelationId) {
        return repository.findAll().stream().filter(e -> e.getTriggerCorrelationId().equals(workflowCorrelationId)).map(e -> toDto(e)).collect(MoreCollectors.toOptional());
    }

    private static WorkflowExecutionDto toDto(final WorkflowRun e) {
        return new WorkflowExecutionDto(
                e.getId(),
                e.getStatus()
        );
    }
}
