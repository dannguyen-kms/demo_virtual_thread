package org.example;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class testHttpServer {
    public static Server createLoomBasedServer(int port) {
        var server = new Server(new VirtualThreadPool());

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);

        server.setConnectors(new Connector[]{connector});
        server.setHandler(new handler());

        return server;
    }

    public static Server createThreadPoolBasedServer(int port) {
        var server = new Server(port);
        server.setHandler(new handler());

        return server;
    }

    public static void main(String[] args) throws Exception {
        var port = 8080;
        Server server = null;
        var serverMode = System.getenv("SERVER_MODE");

        if ("loom".equalsIgnoreCase(serverMode)) {
            server = createLoomBasedServer(port);
        } else if ("standard".equalsIgnoreCase(serverMode)) {
            server = createThreadPoolBasedServer(port);
        } else {
            throw new RuntimeException();
        }

        server.start();
        server.join();
    }
}


