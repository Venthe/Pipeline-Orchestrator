package eu.venthe.platform.projects.plugin.filesystem;

import eu.venthe.platform.projects.plugin.template.PropertyMissingException;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePlugin;
import eu.venthe.platform.projects.plugin.template.RepositorySourcePluginInstance;
import eu.venthe.platform.shared_kernel.dynamic_value.DynamicValue;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@Component
public class FilesystemRepositorySourcePlugin implements RepositorySourcePlugin {
    static final String SOURCE_TYPE = "filesystem";

    @Override
    public String getSourceType() {
        return SOURCE_TYPE;
    }

    @Override
    public RepositorySourcePluginInstance instantiate(Map<String, DynamicValue> properties) {
        // TODO: Lift optional/required properties retrieval to a separate class
        var path = Optional.ofNullable(properties.get("path"))
                .filter(DynamicValue::isString)
                .map(DynamicValue::asString)
                .map(Paths::get)
                .orElseThrow(() -> new PropertyMissingException("path"));
        var maxDepth = Optional.ofNullable(properties.get("depth"))
                .filter(DynamicValue::isInteger)
                .map(DynamicValue::asInteger)
                .orElse(1);
        return new FilesystemRepositorySourcePluginInstance(path, maxDepth);
    }
}
