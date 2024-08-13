package eu.venthe.pipeline.application.config;

import eu.venthe.pipeline.workflow.runs.dependencies.TimeService;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TimeServiceImpl implements TimeService {
    @Override
    public Offset offset() {
        return OffsetDateTime::now;
    }
}
