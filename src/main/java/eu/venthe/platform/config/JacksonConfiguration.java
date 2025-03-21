package eu.venthe.platform.config;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilder dynamicValueJacksonModule() {
        return new Jackson2ObjectMapperBuilder().modules(
                new Jdk8Module()
        );
    }
}
