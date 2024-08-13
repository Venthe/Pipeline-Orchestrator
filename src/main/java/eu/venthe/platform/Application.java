package eu.venthe.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "eu\\.venthe\\.pipeline\\.orchestrator\\._archive\\..*"))
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
