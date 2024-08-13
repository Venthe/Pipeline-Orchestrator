package eu.venthe.pipeline.application;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static class Keycloak {
        public static final String REALM = "orchestrator-integration";
        public static final String CLIENT = "orchestrator-api";

        public static final String ADMINISTRATOR_USER="administrator";
        public static final String ADMINISTRATOR_PASSWORD="secret";
    }
}
