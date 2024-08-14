package eu.venthe.platform.shared_kernel.project;

import eu.venthe.platform.namespace.domain.NamespaceName;
import eu.venthe.platform.source_configuration.SourceConfigurationId;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import eu.venthe.platform.source_configuration.domain.plugins.template.SourceProjectId;
import lombok.Value;
import lombok.experimental.SuperBuilder;

import java.util.regex.Pattern;

@SuperBuilder
@Value
public class ProjectId {
    private static final Pattern PATTERN = Pattern.compile("/^(?:(?<configuration>.+):)?(?:(?<organization>.+?)/)?(?<name>.*\\w)$/", Pattern.CASE_INSENSITIVE);

    NamespaceName namespaceName;
    SourceProjectId name;

    public String serialize() {
        return "%s/%s".formatted(namespaceName.value(), name);
    }

    public static ProjectId from(String projectId) {
        var sourceSeparator = projectId.indexOf(":");
        var orgSeparator = projectId.indexOf("/");

        var builder = ProjectId.builder();
        builder.configurationId(new SourceConfigurationId(extracted(projectId, 0, sourceSeparator)));
        builder.namespaceName(new NamespaceName(extracted(projectId, Math.max(sourceSeparator, 0), orgSeparator)));

        var projectSeparator = Math.max((Math.max(sourceSeparator, 0)), orgSeparator);

        builder.name(projectId.substring(projectSeparator, projectId.length() - 1));

        return builder.build();
    }

    private static String extracted(final String projectId,
                                    final int start,
                                    final int end) {
        var hasElement = end < 0;
        if (hasElement) {
            var substring = projectId.substring(start, end);
            if (substring.isEmpty()) {
                throw new UnsupportedOperationException();
            }

            return substring;
        } else {
            return "default";
        }
    }
}
