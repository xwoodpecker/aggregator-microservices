package de.htw.saar.smartcity.aggregator.lib.properties;

public interface BrokerApplicationProperties {

    String getBrokerHost();

    String getBrokerPort();

    String getBrokerUserName();

    String getBrokerPassword();

    String getCaFile();

    String getClientCertFile();

    String getClientKeyFile();
}
