package net.rustmc.cloud.module.rest;

import net.rustmc.cloud.base.common.Rust;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This file is a JavaDoc!
 * Created: 11/12/2022
 *
 * @author RedCrew
 * Discord: RedCrew#0100
 * Website: https://redcrewtv.de/
 */
public class WebSocket extends WebSocketServer {

    public WebSocket() {
        super(new InetSocketAddress(8080));

    }

    @Override
    public void onOpen(org.java_websocket.WebSocket socket, ClientHandshake handshake) {
        System.out.println("New connection from " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(org.java_websocket.WebSocket socket, int i, String s, boolean b) {
        System.out.println("Closed connection to " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(org.java_websocket.WebSocket socket, String s) {
        String[] args = s.split(":");
        if(args[0].equals("GETSERVER")) {
            System.out.println("UUID: " + args[1]);

            String json = "{" +
                    "\"id\": \"776300d3-ff78-44be-8152-7661df5e541d\"," +
                    "\"name\": \"Test-1\"," +
                    "\"port\": 25565," +
                    "\"group\": \"Test\"," +
                    "\"node\": \"Node-1\"," +
                    "\"slots\": 20," +
                    "\"memory\": 1024," +
                    "\"stats\": {" +
                    " \"player\": 2," +
                    "\"cpu\": 45," +
                    "\"memory\": 512," +
                    "\"tps\": 15" +
                    "}" +
                    "}";

            socket.send(json);
        }
    }

    @Override
    public void onError(org.java_websocket.WebSocket socket, Exception e) {
            System.out.println("ERROR from " + socket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onStart() {

    }
}
