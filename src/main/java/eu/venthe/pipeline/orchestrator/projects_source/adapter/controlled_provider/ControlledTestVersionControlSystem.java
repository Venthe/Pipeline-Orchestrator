package eu.venthe.pipeline.orchestrator.projects_source.adapter.controlled_provider;

import eu.venthe.pipeline.orchestrator.projects_source.adapter.RepositoryReader;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.function.Function;

@Service
public class ControlledTestVersionControlSystem implements RepositoryReader {

    private final Map<FileRef, String> files = new HashMap<>();

    public String addFile(FileRef fileRef, String content) {
        return files.put(fileRef, content);
    }

    @Override
    public Optional<byte[]> getFile(String projectName, String ref, String path) {
        return Optional.ofNullable(files.get(new FileRef(projectName, ref, path)))
                .map(String::getBytes);
    }

    @Override
    public <T> Optional<T> getFile(String projectName, String ref, String path, Function<byte[], T> mapper) {
        throw new UnsupportedOperationException();
    }

    public List<String> getFiles(String project, String ref, String glob) {
        return files.entrySet().stream()
                .filter(e -> e.getKey().ref().equalsIgnoreCase(ref))
                .filter(e -> new AntPathMatcher("/").match(glob, e.getKey().path()))
                .map(Map.Entry::getValue)
                .toList();
    }


    public record FileRef(String projectName, String ref, String path) {
        public FileRef {
            projectName = projectName.toLowerCase(Locale.ROOT);
            ref = ref.toLowerCase(Locale.ROOT);
            path = path.toLowerCase(Locale.ROOT);
        }
    }
}
