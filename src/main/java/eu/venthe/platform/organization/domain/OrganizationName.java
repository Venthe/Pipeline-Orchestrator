package eu.venthe.platform.organization.domain;

public record OrganizationName(String value) {
    public static final OrganizationName DEFAULT = new OrganizationName("DEFAULT");
}
