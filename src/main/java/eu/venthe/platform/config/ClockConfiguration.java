package eu.venthe.platform.config;

import eu.venthe.platform.shared_kernel.ClockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfiguration {
    @Bean
    ClockService clockService() {
        return Clock::systemDefaultZone;
    }
}
