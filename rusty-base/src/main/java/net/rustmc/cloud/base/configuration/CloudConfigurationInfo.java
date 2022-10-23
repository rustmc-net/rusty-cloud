package net.rustmc.cloud.base.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 23.10.2022, So.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CloudConfigurationInfo {

    String name();

}
