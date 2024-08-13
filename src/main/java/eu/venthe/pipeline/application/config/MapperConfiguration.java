package eu.venthe.pipeline.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
