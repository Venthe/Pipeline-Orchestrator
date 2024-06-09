package eu.venthe.pipeline.orchestrator.organizations.domain.source_configuration.plugins.gerrit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
public class GerritWebhook {
    @ResponseBody
    public void handle(@RequestBody String event) {
        log.info("{}", event);
    }
}
