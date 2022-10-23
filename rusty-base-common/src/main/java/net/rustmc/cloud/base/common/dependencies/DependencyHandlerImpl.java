package net.rustmc.cloud.base.common.dependencies;

import lombok.Getter;
import lombok.val;
import net.rustmc.cloud.base.dependencies.Dependency;
import net.rustmc.cloud.base.dependencies.IDependencyHandler;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
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

    public DependencyHandlerImpl() {
        try {
            Files.createDirectory(this.directory.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            val method = this.getClass().getClassLoader().getClass().getMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(this.getClass().getClassLoader(), file.toURI().toURL());
        } catch (NoSuchMethodException | MalformedURLException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        this.running.put(dependency, file);
        return this;
    }

}
