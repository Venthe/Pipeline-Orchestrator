package eu.venthe.platform.source_configuration.plugins.gerrit;

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
