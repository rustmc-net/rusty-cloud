package net.rustmc.cloud.base.common.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.val;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;
import net.rustmc.cloud.base.configuration.ICloudConfigurationHandler;
import net.rustmc.cloud.base.util.Pair;
import net.rustmc.cloud.base.util.operations.UnsafeInstanceOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 23.10.2022, So.
 */
@SuppressWarnings({"DuplicatedCode", "unchecked"})
public final class DefaultCloudConfigurationHandler implements ICloudConfigurationHandler {

    private final LinkedHashMap<String, Pair<URI, CloudConfiguration>> configurations = new LinkedHashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @SuppressWarnings({"ResultOfMethodCallIgnored", "unchecked"})
    @Override
    public <T extends CloudConfiguration> T open(URI uri, Class<? extends T> tClass) {
        if (tClass.isAnnotationPresent(CloudConfigurationInfo.class)) {
            final String name = tClass.getDeclaredAnnotation(CloudConfigurationInfo.class).name();
            if (Files.exists(Paths.get(uri))) {
                final StringBuilder stringBuilder = new StringBuilder();
                try {
                    final FileInputStream inputStream = new FileInputStream(uri.getPath());
                    int character;
                    while ((character = inputStream.read()) != -1) {
                        stringBuilder.append((char) character);
                    }
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                T object = this.gson.fromJson(stringBuilder.toString(), tClass);
                this.configurations.put(name, new Pair<>(uri, object));
                return (T) this.configurations.get(name).getSecond();
            } else {
                final T object;
                try {
                    object = tClass.getConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                final File file = new File(uri);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.configurations.put(name, new Pair<>(uri, object));
                return object;
            }
        } else
            throw new UnsupportedOperationException("The configuration info is missing in type: " + tClass.getName());
    }

    @Override
    public <T extends CloudConfiguration> T open(String name, URI uri, Class<? extends T> tClass) {
        if (Files.exists(Paths.get(uri))) {
            final StringBuilder stringBuilder = new StringBuilder();
            try {
                final FileInputStream inputStream = new FileInputStream(uri.getPath());
                int character;
                while ((character = inputStream.read()) != -1) {
                    stringBuilder.append((char) character);
                }
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            T object = this.gson.fromJson(stringBuilder.toString(), tClass);
            this.configurations.put(name, new Pair<>(uri, object));
            return object;
        } else {
            T object;
            try {
                object = tClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                object = UnsafeInstanceOperations.construct(tClass);
            }
            final File file = new File(uri);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.configurations.put(name, new Pair<>(uri, object));
            return object;
        }
    }

    @Override
    public <T extends CloudConfiguration> T open(String name, URI uri, T object) {
        final File file = new File(uri);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.configurations.put(name, new Pair<>(uri, object));
        return object;
    }

    @Override
    public void close() {
        for (val entry : this.configurations.entrySet())
            this.close(entry.getKey());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void close(String name) {
        final var pair = this.configurations.get(name);
        final File file = new File(pair.getFirst());
        if (file.exists()) {
            try {
                final FileOutputStream outputStream = new FileOutputStream(pair.getFirst().getPath());
                outputStream.write(this.gson.toJson(pair.getSecond()).getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                final FileOutputStream outputStream = new FileOutputStream(pair.getFirst().getPath());
                outputStream.write(this.gson.toJson(pair.getSecond()).getBytes(StandardCharsets.UTF_8));
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
