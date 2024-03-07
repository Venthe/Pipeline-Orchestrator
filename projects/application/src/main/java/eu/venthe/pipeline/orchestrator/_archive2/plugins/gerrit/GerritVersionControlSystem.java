package eu.venthe.pipeline.orchestrator._archive2.plugins.gerrit;

import eu.venthe.pipeline.orchestrator._archive2.plugins.gerrit.utils.GerritHeaders;
import eu.venthe.pipeline.orchestrator.plugins.VersionControlSystem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GerritVersionControlSystem implements VersionControlSystem {
    private final GerritApi gerritApi;
    private final GerritHeaders gerritHeaders;

    @Override
    public Optional<byte[]> getFile(String repositoryId, String projectName, String ref, String path) {
        return gerritApi.getProjects().getFileForBranch(null, projectName, ref, path, gerritHeaders.prepareHeaders(null));
    }
}
