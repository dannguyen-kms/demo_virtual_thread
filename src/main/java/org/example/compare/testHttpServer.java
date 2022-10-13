package org.example.compare;
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
        var port = 8000;
        var server = createThreadPoolBasedServer(port);
        server.start();
        server.join();
    }
}


