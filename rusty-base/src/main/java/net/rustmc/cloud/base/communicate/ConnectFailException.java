package net.rustmc.cloud.base.communicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public class ConnectFailException extends Exception {

    public ConnectFailException() {
        super("Fail to connect to server!");
    }

}
