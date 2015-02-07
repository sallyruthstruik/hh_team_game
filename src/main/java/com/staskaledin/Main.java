package com.staskaledin;

import com.staskaledin.api.DoGame;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class Main {

    public static void main(String[] args) throws Exception{
        URI uri = UriBuilder.fromUri("http://localhost").port(1234).build();
        ResourceConfig config = new ResourceConfig();
        config.register(DoGame.class);

        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, config);

        server.getServerConfiguration().addHttpHandler(
                new StaticHttpHandler("src/script"), "/static"
        );

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Stopping server...");
                server.shutdown();
            }
        }, "shutdownHook"));

        server.start();
        Thread.currentThread().join();
    }
}
