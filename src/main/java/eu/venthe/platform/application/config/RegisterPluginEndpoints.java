package eu.venthe.platform.application.config;

import eu.venthe.platform.project.domain.source_configurations.plugins.template.EndpointProvider;
import eu.venthe.platform.project.domain.source_configurations.plugins.template.ProjectSourcePlugin;
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
                .forEach(pluginInstance ->
                        pluginInstance.provideEndpointMappings().forEach(mapping -> requestMappingHandlerMapping.registerMapping(
                                appendPathPrefix(mapping, pluginInstance.getSourceType().value()),
                                mapping.handler(),
                                mapping.method()
                        ))
                );
    }

    private static RequestMappingInfo appendPathPrefix(EndpointProvider.Mapping mapping, String t) {
        return mapping.requestMappingInfo().mutate()
                .paths(Stream.concat(
                        Stream.of(t),
                        mapping.requestMappingInfo().getDirectPaths().stream()
                ).collect(Collectors.joining()))
                .build();
    }
}
