package eu.venthe.platform.project.domain.source_configurations.plugins.gerrit;

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
