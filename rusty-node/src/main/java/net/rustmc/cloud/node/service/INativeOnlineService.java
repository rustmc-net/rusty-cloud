package net.rustmc.cloud.node.service;

import net.rustmc.cloud.base.service.IOnlineService;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
public interface INativeOnlineService extends IOnlineService {

    public OutputStream getOutputStream();

    public InputStream getInputstream();

    public InputStream getErrorStream();

    public INativeCachedConsole getServiceConsole();

}
