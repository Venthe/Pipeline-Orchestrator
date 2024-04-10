/*
package glue;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import eu.venthe.pipeline.orchestrator.Application;
import io.cucumber.junit.platform.engine.Constants;
import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.spring.ScenarioScope;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Suite
@IncludeEngines("cucumber")
@SelectPackages({"eu.venthe.pipeline.orchestrator"})
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "src/endToEndTest/resources/features")
@SpringBootTest(
        classes = {
                Application.class,
                CucumberSpringGlue.TestConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@CucumberContextConfiguration
public class CucumberSpringGlue {
    static final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:24.0.2").withRealmImportFile("/keycloak/realm.json");

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        keycloak.start();

        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/" + configuration.Constants.Keycloak.REALM);
    }

    @Configuration
    @ComponentScan("fixtures")
    static class TestConfiguration {
        @Bean
        @ScenarioScope
        public dasniko.testcontainers.keycloak.KeycloakContainer keycloakContainer() {
            return keycloak;
        }
    }
}
*/
