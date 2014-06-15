package com.jhc.figleaf.Jobs3RestApi.server;

import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamishdickson on 10/06/2014.
 *
 * Use Grizzly for NIO
 */
public class Server {
    private static URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost/").port(8080).build();
    }

    private static final URI BASE_URI = getBaseUri();

    protected static HttpServer startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages", "com.jhc.figleaf.Jobs3RestApi.resources");

        System.out.println("Starting Grizzly server ....");

        return GrizzlyWebContainerFactory.create(BASE_URI, initParams);
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...",
                BASE_URI, BASE_URI));

        System.in.read();
        httpServer.stop();
    }

}
