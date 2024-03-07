package eu.venthe.pipeline.orchestrator._archive2.plugins.gerrit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import eu.venthe.pipeline.orchestrator._archive2.plugins.gerrit.utils.GerritHeaders;
import eu.venthe.pipeline.orchestrator._archive2.plugins.api.Project;
import eu.venthe.pipeline.orchestrator._archive2.plugins.api.ProjectProvider;
import eu.venthe.pipeline.orchestrator._archive2.plugins.gerrit.config.GerritConfigurationProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@RequiredArgsConstructor
@Service
public class GerritProjectProvider implements ProjectProvider {
    private final GerritApi gerritApi;
    private final ObjectMapper objectMapper;
    private final GerritConfigurationProvider gerritConfigurationProvider;
    private final GerritHeaders gerritHeaders;

    @SneakyThrows
    @Override
    public Collection<Project> getProjects() {
        return gerritConfigurationProvider.getAllConfigurations().entrySet().stream()
                .flatMap(e2 -> {
                    var configuration = e2.getValue();
                    var headers = gerritHeaders.prepareHeaders(configuration);

                    var config = gerritApi.getConfig().getConfig(configuration, headers);

                    var objects = new ArrayList<JsonNode>();
                    var collect = config.get("download").get("schemes").properties().stream()
                            .filter(k -> k.getKey().equals("http") || k.getKey().equals("ssh"))
                            .map(e -> Map.entry(e.getKey(), e.getValue().get("url").textValue().replace("${project}", "%s")))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    var project = StreamSupport.stream(gerritApi.getProjects().getProjects(configuration, headers).spliterator(), false)
                            .map(node -> {
                                ObjectNode newNode = node.deepCopy();
                                // newNode.remove("web_links");
                                String id = newNode.get("id").textValue();
                                newNode.set("systemId", objectMapper.getNodeFactory().textNode(configuration.getPassword()));
                                newNode.set("ssh", objectMapper.getNodeFactory().textNode(collect.get("ssh").formatted(id)));
                                newNode.set("http", objectMapper.getNodeFactory().textNode(collect.get("http").formatted(id)));
                                return newNode;
                            })
                            .collect(Collectors.toSet());
                    objects.addAll(project);

                    return objects.stream();
                })
                .map(e -> {
                    Project project = new Project();
                    Project.Id id = new Project.Id(e.get("systemId").textValue(), e.get("id").textValue().replaceAll("%2F", "/"));
                    project.setStatus(Project.Status.ACTIVE);
                    project.setId(id);
                    return project;
                })
                .collect(Collectors.toList());
    }
}
