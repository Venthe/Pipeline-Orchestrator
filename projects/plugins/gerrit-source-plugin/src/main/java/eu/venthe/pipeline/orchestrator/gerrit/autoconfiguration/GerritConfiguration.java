package eu.venthe.pipeline.orchestrator.gerrit.autoconfiguration;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gerrit")
@Data
@ConditionalOnMissingBean(GerritConfiguration.class)
public class GerritConfiguration {
    private String password;
    private String username;
    private String basePath;
}
