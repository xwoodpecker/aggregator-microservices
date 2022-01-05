package de.htw.saar.smartcity.aggregator.lib.broker;

public interface Publisher {

    void publish(String topic, String message);
}
