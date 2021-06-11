package de.htw.saar.smartcity.aggregator.exporter;

import com.sun.net.httpserver.HttpServer;
import de.htw.saar.smartcity.aggregator.exporter.collector.CustomCollector;
import io.prometheus.client.exporter.HTTPServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {


    public static void main(String[] args) {

        CustomCollector requests = new CustomCollector().register();

        try {
            new HTTPServer("0.0.0.0", 8080, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 80), 1000);
            server.start();


        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
