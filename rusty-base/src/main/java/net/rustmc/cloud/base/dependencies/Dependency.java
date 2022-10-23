package net.rustmc.cloud.base.dependencies;

import lombok.Getter;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public class Dependency {

    private static final String MAVEN_FORMAT = "%s/%s/%s/%s-%s.jar";

    private final String  name, groupID, artifactID, version, mavenPath;

    private Dependency(String name, String groupID, String artifactID, String version) {
        this.name = name;
        this.groupID = groupID;
        this.artifactID = artifactID;
        this.version = version;
        this.mavenPath =  String.format(MAVEN_FORMAT,
                groupID.replace('.', '/'),
                artifactID,
                version,
                artifactID,
                version);
    }

    public static Dependency of(String name, String groupID, String artifactID, String version) {
        return new Dependency(name, groupID, artifactID, version);
    }

    public String getFile() {
        return this.getName().replace('_', '-') + "-" + this.version + ".jar";
    }

}
