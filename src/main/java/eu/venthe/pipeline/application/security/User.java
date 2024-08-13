package eu.venthe.pipeline.application.security;

public record User(String commonName, String role) {

    public static User ADMIN = new User("admin", "administrator");
}
