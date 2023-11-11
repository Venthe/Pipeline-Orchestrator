package eu.venthe.pipeline.pipeline_mediator.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.venthe.pipeline.pipeline_mediator.api.model.EventTriggerDto;
import eu.venthe.pipeline.pipeline_mediator.configuration.YamlMapper;
import eu.venthe.pipeline.pipeline_mediator.domain.plugins.MediatorService;
import eu.venthe.pipeline.pipeline_mediator.domain.model.vo.EventStatus;
import eu.venthe.pipeline.pipeline_mediator.infrastructure.queue.kafka.QueuedEvent;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/event")
public class MediatorController {
    private final MediatorService mediatorService;
    private final YamlMapper yamlMapper;

    @PutMapping(value = "/queue", consumes = {MediaType.APPLICATION_JSON_VALUE, "application/x-yaml"})
    public Optional<QueuedEvent> queueEvent(@RequestBody EventTriggerDto eventTriggerDto) throws JsonProcessingException {
        log.info("Received an event trigger: {}", eventTriggerDto);
        return mediatorService.triggerManualEvent(eventTriggerDto);
    }

    @PostMapping(value = "/retrigger", consumes = {MediaType.TEXT_PLAIN_VALUE})
    public Optional<QueuedEvent> retriggerEvent(@RequestBody String referenceId) {
        log.info("Received an event retrigger for reference ID: {}", referenceId);
        return mediatorService.retriggerEvent(referenceId);
    }

    @PostMapping(value = "/status", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void changeStatus(@RequestBody Status payload) {
        log.info("Setting a status: {}", payload);
        mediatorService.changeStatus(payload);
    }

    @Value
    @Jacksonized
    @Builder(access = AccessLevel.PROTECTED)
    public static class Status {
        @NonNull
        String referenceId;
        @NonNull
        EventStatus status;
    }
}
