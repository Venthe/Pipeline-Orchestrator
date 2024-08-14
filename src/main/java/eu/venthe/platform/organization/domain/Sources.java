package eu.venthe.platform.organization.domain;

import eu.venthe.platform.source_configuration.application.SourceQueryService;
import eu.venthe.platform.source_configuration.domain.model.SourceId;
import eu.venthe.platform.source_configuration.domain.model.SourceOwnedProjectId;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Sources {
    private final SourceQueryService sourceQueryService;
    private final SourceId source;

    public Sources(final SourceId source, final SourceQueryService service) {
        this.source = source;
        this.sourceQueryService = service;
    }

    Set<SourceOwnedProjectId> getAllAvailableProjectIds() {
        return sourceQueryService.getProjectIdentifiers(source).collect(Collectors.toSet());
    }
}
