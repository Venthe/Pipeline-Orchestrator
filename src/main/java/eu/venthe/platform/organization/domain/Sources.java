package eu.venthe.platform.organization.domain;

import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Sources {
    private final SourceQueryService sourceQueryService;
    private final Map<SourceAlias, SourceId> sourceMapping = new HashMap<>();

    public void addConfiguration(SourceId sourceId) {
        addConfiguration(sourceId, SourceAlias.DEFAULT);
    }

    public void addConfiguration(SourceId sourceId, SourceAlias alias) {
        sourceMapping.put(alias, sourceId);
    }

    Set<SourceOwnedProjectId> getAllAvailableProjectIds() {
        return sourceMapping.values().stream().distinct().flatMap(sourceQueryService::getProjectIdentifiers).collect(Collectors.toSet());
    }

    public record SourceAlias(String value) {
        public static final SourceAlias DEFAULT = new SourceAlias("DEFAULT");
    }
}
