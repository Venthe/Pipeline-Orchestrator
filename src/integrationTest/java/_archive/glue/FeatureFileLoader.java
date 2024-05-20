package _archive.glue;

import io.cucumber.gherkin.GherkinParser;
import io.cucumber.messages.types.Envelope;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeatureFileLoader {
    private final GherkinParser parser;

    public FeatureFileLoader(GherkinParser p) {
        parser = p;
    }

    public FeatureFileLoader() {
        this(GherkinParser.builder().build());
    }

    public Set<Envelope> list() throws IOException {
        return list("");
    }

    public Set<Envelope> list(String path) throws IOException {
        List<String> featureFiles = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("file")) {
                File directory = new File(resource.getFile());
                listFeatureFilesRecursive(directory, featureFiles);
            }
        }

        return featureFiles.stream()
                .map(Path::of)
                .flatMap(toEnvelope(parser))
                .collect(Collectors.toSet());
    }

    private static void listFeatureFilesRecursive(File directory, List<String> featureFiles) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFeatureFilesRecursive(file, featureFiles);
                } else {
                    if (file.getName().endsWith(".feature")) {
                        featureFiles.add(file.getAbsolutePath());
                    }
                }
            }
        }
    }

    private static @NonNull Function<Path, Stream<? extends Envelope>> toEnvelope(GherkinParser parser) {
        return p -> {
            try {
                return parser.parse(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
