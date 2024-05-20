package eu.venthe.pipeline.orchestrator.projects_source.plugin.gerrit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.UUID.randomUUID;

@Component
@RequiredArgsConstructor
public class GerritHeaders {
    static String getTraceId() {
        return randomUUID().toString();
    }
}
