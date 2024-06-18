package eu.venthe.pipeline.orchestrator.security;

public record User(String commonName, String role) {

    public static User ADMIN = new User("admin", "administrator");
}
