package eu.venthe.pipeline.orchestrator.projects.domain;

import eu.venthe.pipeline.orchestrator.organizations.domain.OrganizationId;
import eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.SourceConfigurationId;
import lombok.Value;

import javax.annotation.RegEx;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Value(staticConstructor = "of")
public class ProjectId {
    private static final Pattern PATTERN = Pattern.compile("/^(?:(?<configuration>.+):)?(?:(?<organization>.+?)/)?(?<name>.*\\w)$/", Pattern.CASE_INSENSITIVE);

    SourceConfigurationId configurationId;
    OrganizationId organizationId;
    String name;

    public String serialize() {
        return "%s:%s/%s".formatted(configurationId.id(), organizationId.value(), name);
    }

    public static ProjectId from(String projectId) {
        var matcher = PATTERN.matcher(projectId);
        var collect = matcher.namedGroups()
                .entrySet()
                .stream()
                .map(e -> Map.entry(e.getKey(), matcher.group(e.getValue())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue
                ));
        throw new UnsupportedOperationException();
    }
}
