package net.rustmc.cloud.base.common.dependencies;

import lombok.Getter;
import lombok.val;
import net.rustmc.cloud.base.dependencies.Dependency;
import net.rustmc.cloud.base.dependencies.IDependencyHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public final class DependencyHandlerImpl implements IDependencyHandler {

    private final File directory = new File("dependencies");
    private final Map<Dependency, File> running = new HashMap<>();
    private final DynamicClassLoader loader = (DynamicClassLoader) ClassLoader.getSystemClassLoader();
    private final List<Class<?>> loaded = new ArrayList<>();

    public DependencyHandlerImpl() {
        if (!directory.exists()) {
            try {
                Files.createDirectory(this.directory.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public IDependencyHandler throwIn(final Dependency dependency) {
        final var file = new File(this.directory, dependency.getFile());
        if (!file.exists()) {
            try {
                val urlConnection = new URL("https://repo1.maven.org/maven2/" + dependency.getMavenPath())
                        .openConnection();
                try (val inputStream = urlConnection.getInputStream()) {
                    Files.write(file.toPath(), inputStream.readAllBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            loader.add(file.toURI().toURL());
            ArrayList<String> classNames = loader.getClassNamesFromJar(file.getPath());
            for (String name : classNames) {
                if (!name.contains("META")) {
                    System.out.println(name);
                    final var output = loader.loadClass(name);
                    loaded.add(output);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.running.put(dependency, file);
        return this;
    }

    @Override
    public URLClassLoader getClassLoader() {
        return this.loader;
    }

}
