package net.rustmc.cloud.master.common.modules;

import net.rustmc.cloud.base.common.dependencies.DynamicClassLoader;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.modules.IInstanceBuf;
import net.rustmc.cloud.master.modules.IInstanceLoader;
import net.rustmc.cloud.master.modules.RustyCloudModule;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class DefaultInstanceLoaderImpl implements IInstanceLoader {

    private IInstanceBuf[] bufs = new IInstanceBuf[0];
    private final DynamicClassLoader dynamicClassLoader = (DynamicClassLoader) ClassLoader.getSystemClassLoader();

    @Override
    public void push(File file) {
        final var temp = new IInstanceBuf[this.bufs.length+1];
        int i = 0;
        for (IInstanceBuf buf : this.bufs) {
            temp[i] = buf;
            i++;
        }
        try {
            dynamicClassLoader.add(file.toURI().toURL());
            ArrayList<String> classNames = dynamicClassLoader.getClassNamesFromJar(file.getPath());
            for (String name : classNames) {
                if (!name.contains("META")) {
                    final var output = dynamicClassLoader.loadClass(name);
                    final var outputInterface = output.getSuperclass();
                    if (outputInterface.getSimpleName().equals(RustyCloudModule.class.getSimpleName())) {
                        temp[this.bufs.length] = new SimpleInstanceBuf(output);
                        RustCloud.getCloud().getCloudConsole().send(name);
                        this.bufs = temp;
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void load() {
        for (IInstanceBuf buf : this.bufs) {
            buf.boot();
        }
    }

    @Override
    public void terminate() {
        for (IInstanceBuf buf : this.bufs) {
            buf.terminate();
        }
    }

    @Override
    public int modules() {
        return this.bufs.length;
    }

    @Override
    public Iterator<IInstanceBuf> iterator() {
        return new Iterator<>() {
            int r = 1;
            @Override
            public boolean hasNext() {
                return this.r > DefaultInstanceLoaderImpl.this.bufs.length;
            }
            @Override
            public IInstanceBuf next() {
                final var n = DefaultInstanceLoaderImpl.this.bufs[r - 1];
                r++;
                return n;
            }
        };
    }

}
