package eu.venthe.pipeline.application;

import eu.venthe.pipeline.application.configuration.EnvironmentInitializer;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

// Junit 5 extensions DO NOT WORK, as such additional configuration is done with initializer
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = EnvironmentInitializer.class)
class CucumberContext {
}
