package net.rustmc.cloud.base.communicate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketIdentifier {

    char identifier();

}
