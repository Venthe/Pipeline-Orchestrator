package eu.venthe.platform.project.domain;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
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

        var projectIdBuilder = ProjectId.builder();
        projectIdBuilder.namespaceName(projectId.substring(0, namespaceSeparator));
        projectIdBuilder.name(projectId.substring(namespaceSeparator));

        return projectIdBuilder.build();
    }

    public class ProjectIdBuilder {
        public ProjectIdBuilder namespaceName(String namespaceName) {
            this.namespaceName = new NamespaceName(namespaceName);
            return this;
        }

        public ProjectIdBuilder name(String name) {
            this.name = new SourceProjectId(name);
            return this;
        }
    }
}
