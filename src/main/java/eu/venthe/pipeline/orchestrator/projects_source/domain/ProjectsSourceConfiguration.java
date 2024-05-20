package eu.venthe.pipeline.orchestrator.projects_source.domain;

import eu.venthe.pipeline.orchestrator.projects_source.plugin.template.ProjectSourcePlugin;
import eu.venthe.pipeline.orchestrator.shared_kernel.Aggregate;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectsSourceConfiguration implements Aggregate<SourceType> {
    // TODO: Make ID stable
    //  Derive ID either from configuration or assign random
    //  Hash of configuration values?
    @EqualsAndHashCode.Include
    @Getter
    private String id = UUID.randomUUID().toString();
    @EqualsAndHashCode.Include
    private SourceType sourceType;
    private ProjectSourcePlugin.PluginInstance pluginInstance;
    private Collection<KnownProject> knownProjects = new ArrayList<>();
}
