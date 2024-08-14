package eu.venthe.platform.project.domain;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import javax.naming.Name;

@Value
@AllArgsConstructor
public class ProjectId {
    NamespaceName namespaceName;
    SourceProjectId name;

    public String serialize() {
        return "%s/%s".formatted(namespaceName.value(), name.value());
    }

    public static ProjectId from(String projectId) {
        var namespaceSeparator = projectId.indexOf("/");
        if (namespaceSeparator == -1) {
            return new ProjectId(NamespaceName.DEFAULT, new SourceProjectId(projectId));
        }

        return new ProjectId(new NamespaceName(projectId.substring(0, namespaceSeparator)), new SourceProjectId(projectId.substring(namespaceSeparator)));
    }
}
