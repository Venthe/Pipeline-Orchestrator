package eu.venthe.pipeline.orchestrator.projects.domain.source_configurations.plugins.template;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

public interface EndpointProvider {
    default Collection<Mapping> provideEndpointMappings() {
        return Collections.emptySet();
    }

    record Mapping(Object handler, Method method, RequestMappingInfo requestMappingInfo) {
    }
}
