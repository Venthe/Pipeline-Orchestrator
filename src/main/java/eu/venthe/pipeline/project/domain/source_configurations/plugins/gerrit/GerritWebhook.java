package eu.venthe.pipeline.project.domain.source_configurations.plugins.gerrit;

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
