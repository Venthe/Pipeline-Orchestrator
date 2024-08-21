package eu.venthe.platform.project.domain;

import eu.venthe.platform.organization.domain.OrganizationName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.naming.Name;

@Value
@AllArgsConstructor
public class ProjectId {
    OrganizationName organizationName;
    SourceProjectId name;

    public String serialize() {
        return "%s/%s".formatted(organizationName.value(), name.value());
    }

    public static ProjectId from(String projectId) {
        var organizationSeparator = projectId.indexOf("/");
        if (organizationSeparator == -1) {
            return new ProjectId(OrganizationName.DEFAULT, new SourceProjectId(projectId));
        }

        return new ProjectId(new OrganizationName(projectId.substring(0, organizationSeparator)), new SourceProjectId(projectId.substring(organizationSeparator)));
    }
}
