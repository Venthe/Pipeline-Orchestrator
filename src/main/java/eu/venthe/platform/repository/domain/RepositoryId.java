package eu.venthe.platform.repository.domain;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceRepositoryId;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public record RepositoryId(OrganizationName organizationName, SourceRepositoryId name) {
    public String serialize() {
        return "%s/%s".formatted(organizationName.value(), name.value());
    }

    public static RepositoryId from(String repositoryId) {
        var organizationSeparator = repositoryId.indexOf("/");
        if (organizationSeparator == -1) {
            return new RepositoryId(OrganizationName.DEFAULT, new SourceRepositoryId(repositoryId));
        }

        return new RepositoryId(new OrganizationName(repositoryId.substring(0, organizationSeparator)), new SourceRepositoryId(repositoryId.substring(organizationSeparator)));
    }
}
