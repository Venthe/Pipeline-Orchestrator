package eu.venthe.platform.projects;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.*;

@TestConfiguration
public class TestTimeConfiguration {
    public static final ZonedDateTime NOW = ZonedDateTime.of(
            LocalDate.of(2024, Month.APRIL, 3),
            LocalTime.of(13, 33),
            ZoneOffset.UTC
    );
    private static final Clock CLOCK = Clock.fixed(
            NOW.toInstant(),
            ZoneOffset.UTC
    );


    @Bean
    @Primary
    Clock staticClockService() {
        return CLOCK;
    }
}
