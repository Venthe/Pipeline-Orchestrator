package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.modules.automation.workflows.runs.dependencies.TimeService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TimeServiceImpl implements TimeService {
    @Override
    public Offset offset() {
        return OffsetDateTime::now;
    }
}
