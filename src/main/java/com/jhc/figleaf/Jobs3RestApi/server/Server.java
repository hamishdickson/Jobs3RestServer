package com.jhc.figleaf.Jobs3RestApi.server;

import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import com.sun.jersey.api.container.grizzly2.GrizzlyWebContainerFactory;
import com.sun.jersey.spi.container.servlet.ServletContainer;
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
    private static final int PORT = 8070;
    private static final URI BASE_URI = getBaseUri();

    private static URI getBaseUri() {
        return UriBuilder.fromUri("http://localhost/").port(PORT).build();
    }


    protected static HttpServer startServer() throws IOException {
        final Map<String, String> initParams = new HashMap<String, String>();

        initParams.put("com.sun.jersey.config.property.packages", "com.jhc.figleaf.Jobs3RestApi.resources");
        initParams.put("com.wordnik.swagger.jersey.listing", "com.jhc.figleaf.Jobs3RestApi.resources");

        System.out.println("Starting Grizzly server ....");

        return GrizzlyWebContainerFactory.create(BASE_URI, initParams);
    }

    protected static GrizzlyWebServer startGrizzlyServer() throws IOException {
        GrizzlyWebServer grizzlyWebServer = new GrizzlyWebServer(PORT);

        ServletAdapter jerseyAdapter = new ServletAdapter();
        jerseyAdapter.addInitParameter("com.sun.jersey.config.property.packages", "com.jhc.figleaf.Jobs3RestApi.resources");
        jerseyAdapter.addInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters", "com.jhc.figleaf.Jobs3RestApi.server.ResponseCorsFilter");
        jerseyAdapter.setContextPath("/jobs3");
        jerseyAdapter.setServletInstance(new ServletContainer());

        grizzlyWebServer.addGrizzlyAdapter(jerseyAdapter, new String[] {"/jobs3"});

        return grizzlyWebServer;
    }

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

        GrizzlyWebServer gws = startGrizzlyServer();
        gws.start();
        System.in.read();
        httpServer.stop();
        gws.stop();
    }

}
