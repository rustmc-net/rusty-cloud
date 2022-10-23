package net.rustmc.cloud.base.dependencies;

import java.io.File;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface IDependencyHandler {

    public IDependencyHandler throwIn(final Dependency dependency);

    public Map<Dependency, File> getRunning();

}
