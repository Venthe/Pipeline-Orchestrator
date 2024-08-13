package eu.venthe.platform.application.configuration;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import eu.venthe.platform.application.Constants;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.web.context.ConfigurableWebApplicationContext;

public class EnvironmentInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {
    private final KeycloakContainer keycloak = new KeycloakContainer("quay.io/keycloak/keycloak:24.0.2").withRealmImportFile("/keycloak/realm.json");

    @Override
    public void initialize(ConfigurableWebApplicationContext applicationContext) {
        keycloak.start();

        applicationContext.getBeanFactory().registerSingleton("keycloakContainer", keycloak);

        TestPropertyValues.of(
                "spring.security.oauth2.resourceserver.jwt.issuer-uri=" + keycloak.getAuthServerUrl() + "/realms/" + Constants.Keycloak.REALM
        ).applyTo(applicationContext);

        applicationContext.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
            keycloak.stop();
        });
    }
}
