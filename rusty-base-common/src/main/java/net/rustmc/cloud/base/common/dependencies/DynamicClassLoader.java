package net.rustmc.cloud.base.common.dependencies;

import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DynamicClassLoader extends URLClassLoader {

    static {
        registerAsParallelCapable();
    }

    public DynamicClassLoader(String name, ClassLoader parent) {
        super(name, new URL[0], parent);
    }

    /*
     * Required when this classloader is used as the system classloader
     */
    public DynamicClassLoader(ClassLoader parent) {
        this("classpath", parent);
    }

    public DynamicClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public void add(URL url) {
        addURL(url);
    }

    public static DynamicClassLoader findAncestor(ClassLoader cl) {
        do {

            if (cl instanceof DynamicClassLoader)
                return (DynamicClassLoader) cl;

            cl = cl.getParent();
        } while (cl != null);

        return null;
    }

    /*
     *  Required for Java Agents when this classloader is used as the system classloader
     */
    @SuppressWarnings("unused")
    private void appendToClassPathForInstrumentation(String jarfile) throws IOException {
        add(Paths.get(jarfile).toRealPath().toUri().toURL());
    }

    public ArrayList<String> getClassNamesFromJar(JarInputStream jarFile) throws Exception {
        ArrayList<String> classNames = new ArrayList<>();
        try {
            JarEntry jar;
            while (true) {
                jar = jarFile.getNextJarEntry();
                if (jar == null) {
                    break;
                }
                if ((jar.getName().endsWith(".class"))) {
                    String className = jar.getName().replaceAll("/", "\\.");
                    String myClass = className.substring(0, className.lastIndexOf('.'));
                    classNames.add(myClass);
                }
            }
        } catch (Exception e) {
            throw new Exception("Error while getting class names from jar", e);
        }
        return classNames;
    }

    public ArrayList<String> getClassNamesFromJar(String jarPath) throws Exception {
        return getClassNamesFromJar(new JarInputStream(new FileInputStream(jarPath)));
    }

    public void closeClassLoader() {
        URL[] urls = this.getURLs();
        Set<JarFile> closable = new LinkedHashSet<>();
        for(URL url : urls) {
            if(url.getFile().endsWith(".jar")) {
                try {
                    URL jarURL = new URL("jar:" + url.toExternalForm() + "!/");
                    JarURLConnection urlConnection = (JarURLConnection) jarURL.openConnection();
                    JarFile jarFile = urlConnection.getJarFile();
                    closable.add(jarFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        for(JarFile file : closable) {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
