package eu.venthe.pipeline.orchestrator.gerrit;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gerrit")
@Data
@ConditionalOnProperty(prefix = "gerrit", name = "enabled", matchIfMissing = true, havingValue = "true")
public class GerritConfiguration {
    private String password;
    private String username;
    private String basePath;
}
