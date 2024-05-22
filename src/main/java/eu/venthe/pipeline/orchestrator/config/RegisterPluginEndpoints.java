package eu.venthe.pipeline.orchestrator.config;

import eu.venthe.pipeline.orchestrator.projects.plugin.template.EndpointProvider;
import eu.venthe.pipeline.orchestrator.projects.plugin.template.ProjectSourcePlugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class RegisterPluginEndpoints {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public void register(ProjectSourcePlugin.PluginInstance... plugins) {

        Arrays.stream(plugins)
                .forEach(pluginInstance -> pluginInstance.provideEndpointMappings()
                        .forEach(mapping -> requestMappingHandlerMapping.registerMapping(
                                        appendPathPrefix(mapping, pluginInstance.getSourceType().getValue()),
                                        mapping.handler(),
                                        mapping.method()
                                )
                        )
                );
    }

    private static RequestMappingInfo appendPathPrefix(EndpointProvider.Mapping mapping, String t) {
        return mapping.requestMappingInfo().mutate()
                .paths(Stream.concat(
                        Stream.of(t),
                        mapping.requestMappingInfo().getDirectPaths().stream()
                ).collect(Collectors.joining())).build();
    }
}
